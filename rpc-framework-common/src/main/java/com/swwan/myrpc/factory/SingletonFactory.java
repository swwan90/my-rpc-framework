package com.swwan.myrpc.factory;

import jdk.internal.org.objectweb.asm.TypeReference;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SingletonFactory
 * @Description 获取单例对象的工厂类
 * @Author swwan
 * @Date 2020/11/23 10:17
 * @Version 1.0
 **/
public final class SingletonFactory {
    private static final Map<String, Object> OBJECT_MAP = new HashMap<>();

    public SingletonFactory() {
    }

    public static <T> T getInstance(Class<T> c) {
        String key = c.toString();
        Object instance = null;
        if (instance == null) {
            synchronized (SingletonFactory.class) {
                instance = OBJECT_MAP.get(key);
                if (instance == null) {
                    try {
                        instance = c.getDeclaredConstructor().newInstance();
                        OBJECT_MAP.put(key, instance);
                    } catch (IllegalAccessException | InstantiationException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    } catch (NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return c.cast(instance);
    }
}
