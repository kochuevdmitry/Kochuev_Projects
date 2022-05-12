package com.example.aspectdemo;

import org.aspectj.lang.JoinPoint;
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

    @Before(value = "execution(public UuidServiceResponse generate*(..))")
    public void beforeDurationTrackingAdvice(JoinPoint joinPoint) {
        durationMills = new Date().getTime();
        logger.info(joinPoint.toString() + " duration tracking begins");
    }

    @After(value = "execution(public UuidServiceResponse generate*(..))")//две точки, что нам не важно наличие аргументов в методе
    public void afterDurationTrackingAdvice(JoinPoint joinPoint) {
        durationMills = new Date().getTime() - durationMills;
        logger.info(joinPoint.toString() + " generateUuid took: " + durationMills + " mills");
    }

    @Pointcut(value = "execution(* generate*())")
    public void allUuidServicesGenerateMethods() {

    }

    @After("allUuidServicesGenerateMethods()")
    public void execAdviceForAllUuidServiceGenerateMethods(JoinPoint joinPoint) {
        logger.info(joinPoint.getTarget().getClass().getSimpleName() + " was involved");
    }

    @Pointcut(value = "within(com.example.aspectdemo.RandomUuidServiceController)")
    public void allMethodsOfRandomUuidServiceControllerPointcut() {
    }

    @After("allMethodsOfRandomUuidServiceControllerPointcut()")
    public void allMethodsOfRandomUuidServiceControllerAdvice(JoinPoint joinPoint) {
        logger.info("another" + joinPoint.toShortString() + "of Uuid REST service controller invoked");
    }
}
