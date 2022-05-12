package com.example.aspectdemo;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class UuidServicesArgCatcherAspect {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Pointcut(value = "@annotation(com.example.aspectdemo.annotations.ArgsCatchable)")
    public void uuidServicesArgCatcherPointcut() {
    }

    @AfterReturning(pointcut = "args(rnd) && uuidServicesArgCatcherPointcut()", returning = "response")
    public void uuidServicesArgCatcherAdvice(Double rnd, UuidServiceResponse response) {
        response.setUuid(response.getUuid() + "_ADVICE_PART_ADDED");
        logger.info("catched incoming rnd value is: " + rnd);
    }

    @AfterThrowing(pointcut = "args(rnd) && uuidServicesArgCatcherPointcut()", throwing = "ex")
    public void uuidServicesArgCatcherAdvice(Double rnd, Exception ex) {
        logger.info("catched incoming rnd value is: " + rnd+" "+ex.getLocalizedMessage());
    }
}
