package com.swwan.proxy;

import com.swwan.myrpc.entity.RpcServiceProperties;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *  Dynamic proxy class
 *  When a dynamic proxy object calls a method, it actually calls the following invoke method.
 *  It is precisely because of the dynamic proxy that the remote called by the client is like calling the local method(the intermediate process is shielded)
 *
 * @author swwan
 * @date 2020/11/23 14:39
 */
public class RpcClientProxy implements InvocationHandler {

    private static final String INTERFACE_NAME = "interfaceName";

    private final RpcRequestTransport rpcRequestTransport;
    private final RpcServiceProperties rpcServiceProperties;

    public RpcClientProxy(Rp) {
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {




        return null;
    }
}
