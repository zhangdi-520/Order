package com.example.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @version V1.0
 * @program: gulimail
 * @description: TODO
 * @author: Mr.Zhang
 * @create: 2020-07-20 19:48
 **/
@Configuration
public class MyRedissonConfig {


    /*
    * 所有对Redisson的使用都是通过RedissonClient对象
    * */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException {
        //创建配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        //根据config创建RedissonClient实例
        return Redisson.create(config);
    }
}
