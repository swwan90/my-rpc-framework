package com.swwan.myrpc.exception;

import com.swwan.myrpc.enums.RpcErrorMessageEnum;

/**
 * @ClassName RpcException
 * @Description TODO
 * @Author swwan
 * @Date 2020/11/22 21:34
 * @Version 1.0
 **/
public class RpcException extends RuntimeException {
    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum, String detail) {
        super(rpcErrorMessageEnum.getMessage() + ":" + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum) {
        super(rpcErrorMessageEnum.getMessage());
    }
}
