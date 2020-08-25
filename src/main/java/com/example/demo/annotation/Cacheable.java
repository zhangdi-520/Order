package com.example.demo.annotation;

import java.lang.annotation.*;

/**
 * @program: Order
 * @description: redis结果缓存
 * @author: Mr.Wang
 * @create: 2020-08-25 15:33
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cacheable {

    String group() default "cache";

    String name() default "data";

}
