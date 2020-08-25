package com.example.demo.annotation;

import org.springframework.context.annotation.EnableMBeanExport;

import java.lang.annotation.*;

/**
 * @program: Order
 * @description: redis缓存
 * @author: Mr.Wang
 * @create: 2020-08-25 18:12
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheEvict {

    String group() default "cache";

}
