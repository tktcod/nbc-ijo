package com.spring.nbcijo.global;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j(topic = "ControllerLogger")
public class ControllerLogger {

    @Pointcut("execution(* com.spring.nbcijo.controller..*.*(..))")
    private void cut() {}

    @Before("cut()")
    public void before(JoinPoint joinPoint) {
        // 실행되는 클래스의 이름을 가져옴
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // 실행되는 메소드의 이름을 가져옴
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String methodName = method.getName();

        // 메서드에 들어가는 매개변수 배열을 읽어옴
        Object[] args = joinPoint.getArgs();

        // 매개변수 배열의 값을 하나의 문자열로 결합
        String params = Arrays.stream(args)
            .map(obj -> obj.getClass().getSimpleName() + ": " + obj)
            .collect(Collectors.joining(", "));

        // 클래스.메소드(매개변수) 형식으로 출력
        log.info(className + "." + methodName + "(" + params + ")");
    }
}
