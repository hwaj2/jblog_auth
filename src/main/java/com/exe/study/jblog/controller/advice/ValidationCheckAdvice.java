package com.exe.study.jblog.controller.advice;

// AOP(관점지향프로그래밍)를 이용한 유효성 검사

import com.exe.study.jblog.dto.ResponseDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;


@Component
@Aspect
public class ValidationCheckAdvice {

    @Around("execution(* com.exe.study.jblog.controller.*Controller.*(..))")
    public Object validationCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            // 인자 목록에 BindingResult의 객체가 있다면
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();
                    // 에레 메시지를 Map에 저장한다.
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), errorMap);
                }
            }
        }

        return joinPoint.proceed();
    }
}
