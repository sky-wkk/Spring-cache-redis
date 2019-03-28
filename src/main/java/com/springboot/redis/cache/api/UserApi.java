package com.springboot.redis.cache.api;

import com.springboot.redis.cache.model.User;
import com.springboot.redis.cache.service.UserService;
import com.springboot.redis.cache.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author sky
 * @data 2019/3/25 11:06 AM
 */

@RestController
public class UserApi {

    @Autowired
    private  UserService  userService ;


    @PostMapping("/user")
    public  JsonResponse  saveUser(@RequestBody  User user){
        return JsonResponse.success(userService.saveUser(user));
    }


    @GetMapping("/user/{id}")
    public  JsonResponse  selectUser(@PathVariable  String id){
        return JsonResponse.success(userService.selectUser(id));
    }


    @GetMapping("/user/condition/{id}")
    public  JsonResponse  selectUserById(@PathVariable  String id){
        return JsonResponse.success(userService.selectUserById(id));
    }


    @DeleteMapping("/user/{id}")
    public  void  deleteUser(@PathVariable  String id){
        userService.deleteUser(id);
    }

}



