package com.spring.lock.api;

import com.spring.lock.service.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sky
 * @data 2019/4/9 5:45 PM
 */
@RestController
public class LockApi {


    @Autowired
    private LockService  lockService ;



    @GetMapping("/lock")
    public  void  lock(){
        lockService.lock();
    }


    @GetMapping("/lockByCommon")
    public  void  lockByCommon(){
        lockService.lockByCommon();
    }



    @GetMapping("/lockByRedis")
    public  void  lockByRedis(){
        lockService.lockByRedis();
    }



    @GetMapping("/lockByRedisson")
    public  void  lockByRedisson(){
        lockService.lockByRedisson();
    }
}
