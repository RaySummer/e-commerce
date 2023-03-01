package com.eco.commerce.portal.aspect;

import com.eco.commerce.core.annotation.NoRepeatSubmit;
import com.eco.commerce.core.utils.CustomizeException;
import com.eco.commerce.core.utils.WebThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;


/**
 * Author: Ray
 * Date: 2023/02/28
 */
@Slf4j
@Aspect
@Component
public class NoRepeatSubmitAspect {

    @Autowired
    private RedisTemplate<String, Object> objectRedisTemplate;

    @Pointcut("@annotation(com.eco.commerce.core.annotation.NoRepeatSubmit)")
    public void noRepeatSubmit() {
        //Pointcut
    }

    @Before("noRepeatSubmit()")
    public void before(JoinPoint joinPoint) {
        String fingerprint = WebThreadLocal.getBrowserFingerprint(); //portal端浏览器指纹

        if (StringUtils.isBlank(fingerprint)) {
            //portal端没有携带浏览器指纹
            return;
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String key = String.format("%s:%s:%s", className, methodName, fingerprint);

        Object value = objectRedisTemplate.opsForValue().get(key);
        if (value != null) {
            throw new CustomizeException("操作太频繁");
        }

        NoRepeatSubmit noRepeatSubmit = signature.getMethod().getAnnotation(NoRepeatSubmit.class);
        long timeLimit = noRepeatSubmit.timeLimit();
        if (timeLimit <= 0) {
            timeLimit = 1000L;
        }
        objectRedisTemplate.boundValueOps(key).set("1", timeLimit, TimeUnit.MILLISECONDS);
    }
}
