package com.spring.cache.service;

import com.spring.cache.mapper.UserMapper;
import com.spring.cache.model.User;
import com.spring.cache.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author sky
 * @data 2019/3/25 11:06 AM
 */

@Service
public class UserService {

    @Autowired
    private UserMapper  userMapper ;
    /**
     * 存放缓存数据
     * @param user
     * @return
     */
    @CachePut(value = "userCache", key = "#user.id")
    public User saveUser(User user) {
        user.setId(SnowflakeIdWorker.getSnowflakeId());
        user.setCreatedTime(new Date());
        userMapper.insert(user);
        return  user;
    }

    /**
     * 从缓存中获取数据
     * @param id
     * @return
     */
    @Cacheable(value = "userCache", key = "#id",unless="#result == null")
    public User selectUser(String id) {
        User user = userMapper.selectByPrimaryKey(Long.parseLong(id));
        return user;
    }

    /**
     * 组合注解
     * @param id
     * @return
     */
    @Caching(
            cacheable = {@Cacheable(value = "userCache",key = "#id")},
            put = {@CachePut(value = "userCache",key = "#result.name",condition = "#result!=null"),
                    @CachePut(value = "userCache",key = "#result.email",condition = "#result!=null")
            }
    )
    public User selectUserById(String id) {
        User user = userMapper.selectByPrimaryKey(Long.parseLong(id));
        return user;
    }

    /**
     * 清除缓存数据
     * @param id
     */
    @CacheEvict(value = "userCache" ,key = "#id" , allEntries = true)
    public void deleteUser(String id) {
        userMapper.deleteByPrimaryKey(Long.parseLong(id));
    }
}
