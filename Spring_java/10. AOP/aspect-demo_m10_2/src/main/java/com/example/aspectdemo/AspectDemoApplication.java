package com.example.aspectdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)//автоматическое конфигурирование аспектов вместо xml конфигураций
public class AspectDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AspectDemoApplication.class, args);
	}

}
