package com.wzk.bucks.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
// 说明这是一个切面
@Aspect
// 说明这是一个bean
@Component
@Slf4j
public class PerformanceAspect {
//    @Around("execution(* geektime.spring.springbucks.repository..*(..))")
    @Around("repositoryOps()") // 要求参数为:ProceedingJoinPoint
    public Object logPerformance(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        String name = "-";
        String result = "Y";
        try {
            name = pjp.getSignature().toShortString(); // 方法名字
            return pjp.proceed(); // 继续后续的调用
        } catch (Throwable t) {
            result = "N";
            throw t;
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("aop 输出：{};{};{}ms", name, result, endTime - startTime);
        }
    }

    @Pointcut("execution(* com.wzk.bucks.service..*(..))")
//    也可以 在@around里面直接配置拦截
    private void repositoryOps() {
    }
}
