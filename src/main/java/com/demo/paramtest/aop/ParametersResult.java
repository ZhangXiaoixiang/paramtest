package com.demo.paramtest.aop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 这个可以不使用,使用工具类的就可以了,反馈信息写在data
 * RequestRequire:自定义响应对象,用它可以反馈到底哪些参数为空
 * @author zhangxiaoxiang
 * @date: 2019/05/20
 */
@Data//@Data注解在类上，会为类的所有属性自动生成setter/getter、equals、canEqual、hashCode、toString方法，如为final属性，则不会为该属性生成setter方法。
@AllArgsConstructor//@NoArgsConstructor, @RequiredArgsConstructor and @AllArgsConstructor
@NoArgsConstructor
public class ParametersResult<T> {

    private String code;
    private String message;
    private T data;

    public static final String SUCCESS = "S";
    public static final String ERROR = "F";

}
