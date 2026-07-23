package com.pm.billingservice.aop;

import com.pm.billingservice.billing.BillingAccount;
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
public class BillingAccountLoggingAspect {

    @Pointcut("execution(* com.pm.billingservice.billing.BillingAccountService.createAccount(..))")
    public void createAccountPointcut() {
    }

    @Around("createAccountPointcut()")
    public Object logAroundCreateAccount(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Initiating account creation with arguments: {}", Arrays.toString(joinPoint.getArgs()));

        try {
            Object result = joinPoint.proceed();

            if (result instanceof BillingAccount account) {
                log.info("Successfully created Billing Account with ID: {}", account.accountId());
            } else {
                log.info("Successfully created account. Result payload: {}", result);
            }

            return result;
        } catch (Exception e) {
            log.error("Account creation failed. Error: {}", e.getMessage());
            throw e;
        }
    }

}
