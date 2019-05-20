package com.demo.paramtest.entiey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * User:用户实体类
 * @author zhangxiaoxiang
 * @date: 2019/05/20
 */
@Data//@Data注解在类上，会为类的所有属性自动生成setter/getter、equals、canEqual、hashCode、toString方法，如为final属性，则不会为该属性生成setter方法。
@AllArgsConstructor//@NoArgsConstructor, @RequiredArgsConstructor and @AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private Integer age;
    private String addr;
}
