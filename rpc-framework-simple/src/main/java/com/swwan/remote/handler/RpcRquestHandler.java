package com.swwan.remote.handler;

import com.swwan.myrpc.exception.RpcException;
import com.swwan.myrpc.factory.SingletonFactory;
import com.swwan.provider.ServiceProvider;
import com.swwan.provider.ServiceProviderImpl;
import com.swwan.remote.dto.RpcRequest;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName RpcRquestHandler
 * @Description TODO
 * @Author swwan
 * @Date 2020/11/23 15:24
 * @Version 1.0
 **/
@Slf4j
public class RpcRquestHandler {
    private final ServiceProvider serviceProvider;

    public RpcRquestHandler() {
        this.serviceProvider = SingletonFactory.getInstance(ServiceProviderImpl.class);
    }

    public Object handle(RpcRequest request) {
        Object service = serviceProvider.getService(request.toRpcProperties());
        return invokeTargetMethod(request, service);
    }

    private Object invokeTargetMethod(RpcRequest request, Object service) {
        Object result;
        try {
            Method method = service.getClass().getMethod(request.getMethodName(), request.getParamTypes());
            result = method.invoke(service, request.getParameters());
            log.info("service:[{}] successful invoke method:[{}]", request.getInterfaceName(), request.getMethodName());
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            throw new RpcException(e.getMessage(), e);
        }
        return result;
    }


}
