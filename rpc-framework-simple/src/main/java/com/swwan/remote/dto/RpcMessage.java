package com.swwan.remote.dto;

import lombok.*;

/**
 * @ClassName RpcMessage
 * @Description TODO
 * @Author swwan
 * @Date 2020/11/23 14:51
 * @Version 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcMessage {
    private byte messageType;
    private byte codec;
    private byte compress;
    private int requestId;
    private Object data;
}
