package com.swwan.remote.dto;

import com.swwan.myrpc.entity.RpcServiceProperties;
import lombok.*;

import java.io.Serializable;

/**
 * @ClassName RpcRequest
 * @Description TODO
 * @Author swwan
 * @Date 2020/11/23 14:53
 * @Version 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private String requestId;
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
    private String version;
    private String group;

    public RpcServiceProperties toRpcProperties() {
        return RpcServiceProperties.builder().serviceName(this.getInterfaceName())
                .version(this.getVersion())
                .group(this.getGroup()).build();
    }
}
