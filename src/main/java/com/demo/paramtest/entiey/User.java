package com.demo.paramtest.entiey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//@Data注解在类上，会为类的所有属性自动生成setter/getter、equals、canEqual、hashCode、toString方法，如为final属性，则不会为该属性生成setter方法。
@AllArgsConstructor//@NoArgsConstructor, @RequiredArgsConstructor and @AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private Integer age;
    private String addr;

    public static void main(String[] args) {
        User user=new User(1,"张晓祥",12,"重庆江北区");
        System.out.println(user);
    }
}
