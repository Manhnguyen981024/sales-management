package com.demo.productservice.aop;

import com.demo.productservice.entity.AuditLog;
import com.demo.productservice.repositoy.AuditLogRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class LoggingAspect {
    private final AuditLogRepository auditLogRepository;

    @Pointcut("execution(* com.demo.productservice.service.ProductService.*(..))")
    public void productServiceMethods(){}

    @Before("productServiceMethods()")
    public void logBefore(JoinPoint joinPoint){
        log.info("[Before] Calling method: {}", joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info("[Before] arg: {}", arg);
        }
    }

    @AfterReturning(pointcut = "productServiceMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result){
        log.info("✅ [After] Method {} returned: {}", joinPoint.getSignature().getName(), result);

        String methodName = joinPoint.getSignature().getName();
        String arguments = Arrays.stream(joinPoint.getArgs())
                .map(arg -> arg != null ? arg.toString() : "null")
                .collect(Collectors.joining(", "));

        // 👤 Giả lập actor (ở thực tế lấy từ SecurityContext)
        String actor = "USER"; // Hoặc lấy từ SecurityContextHolder.getContext().getAuthentication().getName()
        AuditLog log = new AuditLog();
        log.setActor(actor);
        log.setAction("CALL_METHOD");
        log.setMethodName(methodName);
        log.setArguments(arguments);

        auditLogRepository.save(log);
    }

    @AfterThrowing(pointcut = "productServiceMethods()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        log.error("❌ [Error] Method {} threw exception: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }
}
