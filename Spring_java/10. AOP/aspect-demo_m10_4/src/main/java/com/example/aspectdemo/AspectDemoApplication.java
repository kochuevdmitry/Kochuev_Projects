package com.example.aspectdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AspectDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AspectDemoApplication.class, args);
	}

}
