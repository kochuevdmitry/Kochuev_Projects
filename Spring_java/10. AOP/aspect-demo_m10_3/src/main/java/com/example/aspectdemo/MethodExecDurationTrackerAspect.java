package com.example.aspectdemo;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

@Aspect
@Component
public class MethodExecDurationTrackerAspect {

    private Long durationMills;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Before(value = "execution(public UuidServiceResponse generateUuid())")
    public void beforeDurationTrackingAdvice() {
        durationMills = new Date().getTime();
        logger.info("duration tracking begins");
    }

    @After(value = "execution(public UuidServiceResponse generateUuid())")
    public void afterDurationTrackingAdvice() {
        durationMills = new Date().getTime() - durationMills;
        logger.info("generateUuid took: " + durationMills + " mills");
    }

    @Pointcut(value = "execution(* generate*())")//все методы которые начинаются со слова generate
    public void allUuidServicesGenerateMethods() {}

    @After("allUuidServicesGenerateMethods()")
    public void execAdviceForAllUuidServiceGenerateMethods() {
        logger.info("advice to all generation methods invoked!");
    }

    @Pointcut(value = "within(com.example.aspectdemo.RandomUuidServiceController)")//все методы класса RandomUuidServiceController
    public void allMethodsOfRandomUuidServiceControllerPointcut() {
    }

    @After("allMethodsOfRandomUuidServiceControllerPointcut()")
    public void allMethodsOfRandomUuidServiceControllerAdvice() {
        logger.info("another endpoint of Uuid REST service invoked");
    }
}
