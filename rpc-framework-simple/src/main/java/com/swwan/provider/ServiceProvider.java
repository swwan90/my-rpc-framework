package com.swwan.provider;

import com.swwan.myrpc.entity.RpcServiceProperties;

import java.util.Objects;

/**
 * @ClassName ServiceProvider
 * @Description TODO
 * @Author swwan
 * @Date 2020/11/23 14:23
 * @Version 1.0
 **/
public interface ServiceProvider {

    void addService(Object service, Class<?> serviceClass, RpcServiceProperties rpcServiceProperties);

    Object getService(RpcServiceProperties rpcServiceProperties);

    void publishService(Object service, RpcServiceProperties rpcServiceProperties);

    void publishService(Object service);

}
