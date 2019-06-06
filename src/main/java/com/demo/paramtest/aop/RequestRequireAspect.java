package com.demo.paramtest.aop;


import com.demo.paramtest.util.JsonResult;
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
import java.util.HashMap;
import java.util.Map;
/**
 * SetPropertyName:切面类
 * @author zhangxiaoxiang
 * @date: 2019/05/20
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
     *
     * @return java.lang.Object
     * @throws
     * @params pjp
     **/
    @Around("controllerInteceptor()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //参数非空返回给前端的
        JsonResult<Object> jr=new JsonResult<>();
        Map map = new HashMap();

        // 获取注解的方法参数列表
        Object[] args = pjp.getArgs();

        // 获取被注解的方法
        MethodInvocationProceedingJoinPoint mjp = (MethodInvocationProceedingJoinPoint) pjp;
        MethodSignature signature = (MethodSignature) mjp.getSignature();
        Method method = signature.getMethod();

        // 获取方法上的注解
        RequestRequire require = method.getAnnotation(RequestRequire.class);

        // 以防万一，将中文的逗号替换成英文的逗号,并且把空格去掉了
        String fieldNames = require.require().replace("，", ",").replace(" ","");


        // 从参数列表中获取参数对象
        Object parameter = null;
        for (Object pa : args) {
            //class相等表示是同一个对象
            if (pa.getClass() == require.parameter()) {
                parameter = pa;
            }
        }

        // 通过反射去和指定的属性值判断是否非空
        // 获得参数的class
        Class aClass = parameter.getClass();



        // 遍历参数，找到是否为空
        for (String name : fieldNames.split(split)) {
            //需要判断接口参数是否和请求的对象属性一致,所以异常包裹一下
            Field declaredField = null;
            try {
                declaredField = aClass.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                jr.setCode("F");
                jr.setMessage("后台接口非空参数错误:"+name);
                e.printStackTrace();
                return jr;
            } catch (SecurityException e) {

                e.printStackTrace();
            }
            String fieldName = declaredField.getName();
            //反射的常规操作
            declaredField.setAccessible(true);
            Object fieldObject = declaredField.get(parameter);
            // 获取属性的中文名称
            SetPropertyName spv = declaredField.getAnnotation(SetPropertyName.class);
            if (spv != null && StringUtils.isNotBlank(spv.value())) {
                fieldName = spv.value();
            }
            System.out.println(fieldName+"------====");
            if (fieldObject == null) {
                System.out.println(fieldObject+"------");
                map.put(fieldName, "该参数不能为空!");
                jr.setData(map);
                continue;
            }

            // 如果type是类类型，则前面包含"class "，后面跟类名
            if (declaredField.getGenericType().toString().equals("class java.lang.String")) {
                if (StringUtils.isBlank((String) fieldObject)) {
                    map.put(fieldName, "该参数不能为空!");
                    jr.setData(map);

                    continue;


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


            System.out.println(fieldName+"是传入了的");


        }

        System.out.println(jr);
        if (jr != null&&jr.getData()!=null) {
            jr.setCode("F");
            jr.setMessage("进入参数非空判断,详情见data!");


            return jr;
        }
        // 如果没有报错，放行
        return pjp.proceed();
    }
}
