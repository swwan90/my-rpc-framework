package com.swwan.remote.transport.netty.server;

import com.swwan.myrpc.enums.CompressTypeEnum;
import com.swwan.myrpc.enums.RpcResponseCodeEnum;
import com.swwan.myrpc.enums.SerializationTypeEnum;
import com.swwan.myrpc.factory.SingletonFactory;
import com.swwan.remote.constants.RpcConstants;
import com.swwan.remote.dto.RpcMessage;
import com.swwan.remote.dto.RpcRequest;
import com.swwan.remote.dto.RpcResponse;
import com.swwan.remote.handler.RpcRquestHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName NettyRpcServerHandler
 * @Description Customize the ChannelHandler of the server to process the data sent by the client
 *
 * 如果继承自 SimpleChannelInboundHandler 的话，就不要考虑 ByteBuf 的释放。{@link io.netty.channel.SimpleChannelInboundHandler} 内部的
 * channelRead 方法会替你释放 ByteBuf。避免可能导致的内存泄露问题
 * @Author swwan
 * @Date 2020/11/24 16:46
 * @Version 1.0
 **/
@Slf4j
public class NettyRpcServerHandler extends ChannelInboundHandlerAdapter {
    private final RpcRquestHandler rpcRequestHandler;

    public NettyRpcServerHandler() {
        this.rpcRequestHandler = SingletonFactory.getInstance(RpcRquestHandler.class);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (!(msg instanceof RpcMessage)) {
                return;
            }
            log.info("server receive msg: [{}]", msg);
            RpcMessage rpcMessage = new RpcMessage();
            rpcMessage.setCodec(SerializationTypeEnum.PROTOSTUFF.getCode());
            rpcMessage.setCompress(CompressTypeEnum.GZIP.getCode());
            if (checkAndhandlePINGRequest((RpcMessage) msg, rpcMessage)) {
                return;
            }
            rpcMessage.setData(getMessageData(ctx, rpcMessage));
            ctx.writeAndFlush(rpcMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        } finally {
            // Ensure that ByteBuf is released, otherwise there may be memory leaks
            ReferenceCountUtil.release(msg);
        }
    }

    private boolean checkAndhandlePINGRequest(RpcMessage msg, RpcMessage rpcMessage) {
        byte messageType = msg.getMessageType();
        if (messageType == RpcConstants.HEARTBEAT_REQUEST_TYPE) {
            rpcMessage.setMessageType(RpcConstants.HEARTBEAT_RESPONSE_TYPE);
            rpcMessage.setData(RpcConstants.PONG);
            return true;
        }
        return false;
    }

    private RpcResponse<Object> getMessageData(ChannelHandlerContext ctx, RpcMessage rpcMessage) {
        RpcRequest rpcRequest = (RpcRequest) (rpcMessage).getData();
        // Execute the target method (the method the client needs to execute) and return the method result
        Object result = rpcRequestHandler.handle(rpcRequest);
        log.info(String.format("server get result: %s", result.toString()));
        rpcMessage.setMessageType(RpcConstants.RESPONSE_TYPE);
        RpcResponse<Object> rpcResponse = null;
        if (ctx.channel().isActive() && ctx.channel().isWritable()) {
            rpcResponse = RpcResponse.success(result, rpcRequest.getRequestId());
        } else {
            rpcResponse = RpcResponse.fail(RpcResponseCodeEnum.FAIL);
            log.error("not writable now, message dropped");
        }
        return rpcResponse;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                log.info("idle check happen, so close the connection");
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("server catch exception");
        cause.printStackTrace();
        ctx.close();
    }
}
