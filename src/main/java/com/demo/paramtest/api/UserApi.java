package com.demo.paramtest.api;

import com.demo.paramtest.aop.RequestRequire;
import com.demo.paramtest.entiey.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApi {

    @RequestMapping("/test")
    @RequestRequire(require="name,id",parameter = User.class)
    public Object test(@RequestBody User user){
        System.out.println(user);
        return user;
    }
}
