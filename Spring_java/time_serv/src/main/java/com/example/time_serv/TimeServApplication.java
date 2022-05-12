package com.example.time_serv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

/* //три анотации внизу тоже самое, что одна вверху
@SpringBootConfiguration
@ComponentScan
@EnableAutoConfiguration*/

//мы переместили TimeService В другую папку и без анотации поиска компонентов проект его не найдет
@ComponentScan(basePackages = "com.example")
public class TimeServApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeServApplication.class, args);
	}

}
