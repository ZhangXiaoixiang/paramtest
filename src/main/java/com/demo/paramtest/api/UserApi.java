package com.demo.paramtest.api;

import com.demo.paramtest.aop.RequestRequire;
import com.demo.paramtest.entiey.User;
import com.demo.paramtest.util.JsonResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserApi:用户接口
 *
 * @author zhangxiaoxiang
 * @date: 2019/05/20
 */
@RestController
public class UserApi {

    @RequestMapping("/test")
    //接口判断参数

    @RequestRequire(require = "id,name,addr", parameter = User.class)
    public JsonResult<User> test(@RequestBody User user) {

        JsonResult<User> jr = new JsonResult<>();
        jr.setCode("S");
        jr.setMessage("查询接口成功!");
        //模拟用户查询的结果
        user.setAge(18);
        jr.setData(user);
        System.out.println(user);
        return jr;
    }
}
