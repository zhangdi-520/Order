package com.example.demo.annotation;

import com.example.demo.entity.OrderEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @version V1.0
 * @program: Order
 * @description: TODO
 * @author: Mr.Zhang
 * @create: 2020-08-25 15:35
 **/
@Aspect
@Component
public class CacheAspect {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    RedisTemplate redisTemplate;



    @Around("@annotation(com.example.demo.annotation.Cacheable)")
    public Object cache(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Cacheable annotation = method.getAnnotation(Cacheable.class);
        RLock lock = redissonClient.getLock("cache");
        lock.lock();
        try {
            Object orderEntity = null;
            if ("methodName".equals(annotation.name())) {
                orderEntity = redisTemplate.opsForHash().get(annotation.group(), method.getParameters()[0].toString());
            }else {
                orderEntity = redisTemplate.opsForHash().get(annotation.group(), annotation.name());
            }
            if (orderEntity!=null){
                return orderEntity;
            }else{
                OrderEntity proceed = (OrderEntity)joinPoint.proceed();
                if ("methodName".equals(annotation.name())) {
                    redisTemplate.opsForHash().put(annotation.group(), method.getParameters()[0].toString(),proceed.toString());
                }else{
                    redisTemplate.opsForHash().put(annotation.group(), annotation.name(),proceed.toString());
                }
                return proceed;
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }finally {
            lock.unlock();
        }
        return null;
    }

    @Around("@annotation(com.example.demo.annotation.CacheEvict)")
    public Boolean cacheEvict(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CacheEvict annotation = method.getAnnotation(CacheEvict.class);
        RLock lock = redissonClient.getLock("RealseCache");
        lock.lock();
        try {
            redisTemplate.delete(annotation.group());
            return (Boolean) joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }finally {
            lock.unlock();
        }
        return null;
    }
}
