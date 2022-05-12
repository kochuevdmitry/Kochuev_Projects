package com.example.aspectdemo;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
}
