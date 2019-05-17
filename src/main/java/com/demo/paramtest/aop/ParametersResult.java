package com.demo.paramtest.aop;

import com.demo.paramtest.util.JsonResult;

/**
 * 自定义响应对象
 * 用它可以反馈到底哪些参数为空
 */
public class ParametersResult {
    public static JsonResult<String> error(String s) {
        JsonResult<String> jr=new JsonResult<>();
        jr.setCode("F");
        jr.setMessage(s);
        return jr;
    }
}
