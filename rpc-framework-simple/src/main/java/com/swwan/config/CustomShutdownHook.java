package com.swwan.config;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName CustomShutdownHook
 * @Description 服务停止时，触发一些动作
 * @Author swwan
 * @Date 2020/11/23 14:01
 * @Version 1.0
 **/
@Slf4j
public class CustomShutdownHook {
    private static final CustomShutdownHook CUSTOM_SHUTDOWN_HOOK = new CustomShutdownHook();

    public static CustomShutdownHook getCustomShutdownHook() {
        return CUSTOM_SHUTDOWN_HOOK;
    }

    public void clearAll() {
        log.info("addShutdownHook for clearAll");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

        }));
    }
}
