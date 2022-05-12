package com.example.aspectdemo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

@Aspect
@Component
public class MethodExecDurationTrackerAspect {

    private Long durationMills;
    private Logger logger = Logger.getLogger(this.getClass().getName());

//    @Before(value = "execution(public UuidServiceResponse generate*(..))")
//    public void beforeDurationTrackingAdvice(JoinPoint joinPoint) {
//        durationMills = new Date().getTime();
//        logger.info(joinPoint.toString() + " duration tracking begins");
//    }
//
//    @After(value = "execution(public UuidServiceResponse generate*(..))")
//    public void afterDurationTrackingAdvice(JoinPoint joinPoint) {
//        durationMills = new Date().getTime() - durationMills;
//        logger.info(joinPoint.toString() + " generateUuid took: " + durationMills + " mills");
//    }

    @Around(value = "@annotation(com.example.aspectdemo.annotations.DurationTrackable))")
    public Object aroundDurationTrackingAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        durationMills = new Date().getTime();
        logger.info(proceedingJoinPoint.toString() + " duration tracking begins");
        Object returnValue = null;
        try {
            returnValue = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        durationMills = new Date().getTime() - durationMills;
        logger.info(proceedingJoinPoint.toString() + " generateUuid took: " + durationMills + " mills");
        return returnValue;
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
