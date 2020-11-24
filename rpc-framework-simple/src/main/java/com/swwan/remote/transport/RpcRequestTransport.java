package com.swwan.remote.transport;

import com.swwan.myrpc.extension.SPI;
import com.swwan.remote.dto.RpcRequest;

@SPI
public interface RpcRequestTransport {

    Object sendRpcRequest(RpcRequest request);
}
