package com.example.aspectdemo;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class UuidServicesArgCatcherAspect {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Pointcut(value = "within(com.example.aspectdemo.*)")
    public void uuidServicesArgCatcherPointcut() {
    }

    @Before(value = "args(rnd) && uuidServicesArgCatcherPointcut()")//отлавливаем использование переменной rnd в(&&)  рамках поинта
    public void uuidServicesArgCatcherAdvice(Double rnd) {

        logger.info("catched incoming rnd value is: " + rnd);
    }
}
