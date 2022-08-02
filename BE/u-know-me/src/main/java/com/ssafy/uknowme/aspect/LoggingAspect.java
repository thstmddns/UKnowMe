package com.ssafy.uknowme.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.ssafy.uknowme.web.service.*.get*(..))")
    public void loggerBefore(){
        System.out.println("get으로 시작하는 메서드 시작");
    }

    @Before("execution(* com.ssafy.uknowme.web.service.*.get*(..))")
    public void loggerAfter(){
        System.out.println("get으로 시작하는 메서드 Rmx");
    }

    @Around("execution(* com.ssafy.uknowme.web.controller.MemberController*.get*(..))")
    public Object loggerAround(ProceedingJoinPoint pjp) throws Throwable {
        long beforeTimeMillis = System.currentTimeMillis();
        System.out.println("[MemberController] 실행시작 : "
            +pjp.getSignature().getDeclaringTypeName() + "."
            +pjp.getSignature().getName());
        Object result = pjp.proceed();

        long afterTimeMillis = System.currentTimeMillis() - beforeTimeMillis;
        System.out.println("[MemberController] 실행완료" + afterTimeMillis + "밀리초 소요"
                +pjp.getSignature().getDeclaringTypeName() + "."
                +pjp.getSignature().getName());
        return result;
    }
}
