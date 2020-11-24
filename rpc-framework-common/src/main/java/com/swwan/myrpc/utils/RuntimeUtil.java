package com.swwan.myrpc.utils;

/**
 * @ClassName RuntimeUtil
 * @Description TODO
 * @Author swwan
 * @Date 2020/11/23 11:34
 * @Version 1.0
 **/
public class RuntimeUtil {
    // 获取 CPU 的核心数
    public static int cpus() {
        return Runtime.getRuntime().availableProcessors();
    }
}
