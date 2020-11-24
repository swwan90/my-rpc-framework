package com.swwan.annotation;

import java.lang.annotation.*;

/**
 * @ClassName RpcReference
 * @Description TODO
 * @Author swwan
 * @Date 2020/11/23 11:45
 * @Version 1.0
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RpcReference {

    String version() default "";

    String group() default "";
}
