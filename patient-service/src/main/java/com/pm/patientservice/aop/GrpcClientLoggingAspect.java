package com.pm.patientservice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class GrpcClientLoggingAspect {

    @Pointcut("within(com.pm.patientservice.domains.grpc.BillingServiceGrpcClient)")
    public void grpcClientLoggingAspect() {}

    @Around("grpcClientLoggingAspect()")
    public Object logAroundGrpcCall(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();

        log.info("Initiating gRPC call: {}() with arguments: {}", methodName, Arrays.toString(joinPoint.getArgs()));

        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - startTime;

            log.info("Successfully completed gRPC call: {}() in {} ms", methodName, elapsedTime);
            return result;
        } catch (Exception e) {
            log.error("Exception during gRPC call: {}() with arguments: {}. Exception: {}", methodName, Arrays.toString(joinPoint.getArgs()), e.getMessage(), e);
            throw e;
        }
    }
}
