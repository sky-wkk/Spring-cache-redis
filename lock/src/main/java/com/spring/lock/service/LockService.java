package com.spring.lock.service;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author sky
 * @data 2019/4/9 5:46 PM
 */
@Service
public class LockService {


    @Autowired
    private StringRedisTemplate   stringRedisTemplate ;


    @Autowired
    private Redisson redisson ;


    /**
     * 普通代码实现
     */
    public  void lock(){
        String  goods = stringRedisTemplate.opsForValue().get("goods");
        Integer  orange = Integer.valueOf(goods);
        if(Integer.valueOf(orange)<=0){
            System.out.println("库存不足!");
            return;
        }
        orange = orange -1;
        System.out.println("剩余库存:"+orange);
        stringRedisTemplate.opsForValue().set("goods",orange+"");
    }

    /**
     * 普通同步锁
     */
    public  void lockByCommon(){
        synchronized (this){
            String  goods = stringRedisTemplate.opsForValue().get("goods");
            Integer  orange = Integer.valueOf(goods);
            if(Integer.valueOf(orange)<=0){
                System.out.println("库存不足!");
                return;
            }
            orange = orange -1;
            System.out.println("剩余库存:"+orange);
            stringRedisTemplate.opsForValue().set("goods",orange+"");
        }
    }


    /**
     * 分布式锁 使用redis 命令 SETNX
     */
    public void  lockByRedis(){
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("LOCK", "001", 10, TimeUnit.SECONDS);
        //当key存在,其他线程需等待其被释放才可以执行
        if(!lock) {
            return;
        }
        //*** 执行业务代码
        try {
            String  goods = stringRedisTemplate.opsForValue().get("goods");
            Integer  orange = Integer.valueOf(goods);
            if(Integer.valueOf(orange)<=0){
                System.out.println("库存不足!");
                return;
            }
            orange = orange -1;
            stringRedisTemplate.opsForValue().set("goods",orange+"");
            System.out.println("剩余库存:"+orange);
        }catch (Exception ex){
                ex.printStackTrace();
        }finally {
            stringRedisTemplate.delete("LOCK");
        }
    }


    /**
     * 分布式锁 使用redisson 服务解决分布式锁
     */
    public  void  lockByRedisson(){
        //获取锁
        RLock lock = redisson.getLock("LOCK");
        try {
            //加锁
            lock.lock(60, TimeUnit.SECONDS);
            String  goods = stringRedisTemplate.opsForValue().get("goods");
            Integer  orange = Integer.valueOf(goods);
            if(Integer.valueOf(orange)<=0){
                System.out.println("库存不足!");
                return;
            }
            orange = orange -1;
            stringRedisTemplate.opsForValue().set("goods",orange+"");
            System.out.println("剩余库存:"+orange);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            //解锁
            lock.unlock();
        }
    }


}
