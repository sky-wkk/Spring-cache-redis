package com.spring.lock.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author sky
 * @data 2019/4/10 11:21 AM
 */
@Configuration
public class redisManager {


    @Bean
    public Redisson redisson(){
        Config  config = new Config();
        config.useSingleServer().setAddress("redis://10.10.20.37:6379").setDatabase(1);
        return (Redisson) Redisson.create(config);
    }


//    @Bean
//    public StringRedisTemplate  stringRedisTemplate(RedisConnectionFactory  redisConnectionFactory){
//        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
//        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
//        return  stringRedisTemplate ;
//        }
}
