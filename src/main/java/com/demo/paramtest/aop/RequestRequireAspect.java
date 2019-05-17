package com.demo.paramtest.aop;


import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 编写切面类
 */
@Component
@Aspect
public class RequestRequireAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestRequireAspect.class);

    static final String split = ",";

    @Pointcut("@annotation(com.demo.paramtest.aop.RequestRequire)")
    public void controllerInteceptor() {

    }

    /**
     * controller层增强类，用于检测参数为空的情况,低侵入式,不影响已经写好的代码
     * 目前不能够一次性通知所有的非空,只能按顺序遇到非空就返回,直到全部非空参数(控制层注解标注的非空参数)都判断完,确实没有非空参数了才放行
     * 对前端人员来说可能不是很好,但是不影响后端判断
     * @params pjp
     * @throws
     * @return java.lang.Object
     **/
    @Around("controllerInteceptor()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        // 获取注解的方法参数列表
        Object[] args = pjp.getArgs();

        // 获取被注解的方法
        MethodInvocationProceedingJoinPoint mjp = (MethodInvocationProceedingJoinPoint) pjp;
        MethodSignature signature = (MethodSignature) mjp.getSignature();
        Method method = signature.getMethod();

        // 获取方法上的注解
        RequestRequire require = method.getAnnotation(RequestRequire.class);

        // 以防万一，将中文的逗号替换成英文的逗号
        String fieldNames=require.require().replace("，", ",");

        // 从参数列表中获取参数对象
        Object parameter=null;
        for(Object pa:args){
            //class相等表示是同一个对象
            if (pa.getClass()==require.parameter() ) {
                parameter=pa;
            }
        }

        // 通过反射去和指定的属性值判断是否非空
        // 获得参数的class
        Class aClass = parameter.getClass();

        //Map组装非空参数

        // 遍历参数，找到是否为空
        for(String name:fieldNames.split(split)){
            Field declaredField = aClass.getDeclaredField(name);
            String fieldName = declaredField.getName();
            declaredField.setAccessible(true);//反射的常规操作
            Object fieldObject = declaredField.get(parameter);
            // 获取属性的中文名称
            SetPropertyName spv = declaredField.getAnnotation(SetPropertyName.class);
            if(spv != null && StringUtils.isNotBlank(spv.value())){
                fieldName = spv.value();
            }

            if(fieldObject == null){

                return ParametersResult.error("参数 " + fieldName + " 不能为空");
            }
            // 如果type是类类型，则前面包含"class "，后面跟类名
            if (declaredField.getGenericType().toString().equals("class java.lang.String")) {
                if(StringUtils.isBlank((String)fieldObject)){
                    return ParametersResult.error("参数 " + fieldName + " 不能为空");
                }
            }
            // 如果是数字类型的---留着拓展的
/*           if (declaredField.getGenericType().toString().equals("class java.lang.Integer")
                  || declaredField.getGenericType().toString().equals("class java.lang.Long")
                   || declaredField.getGenericType().toString().equals("class java.lang.Double")
                  || declaredField.getGenericType().toString().equals("class java.lang.Float")) {
                if(fieldObject == null){
                    return R.error("参数" + fieldName + "不能为空");
               }
            } */

            System.out.println("要求非空参数  "+fieldName);
        }
        // 如果没有报错，放行
        return pjp.proceed();
    }
}
