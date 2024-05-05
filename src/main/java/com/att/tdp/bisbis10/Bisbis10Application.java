package com.att.tdp.bisbis10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = "com.att.tdp.bisbis10.orderItem")
public class Bisbis10Application {

	public static void main(String[] args) {
		SpringApplication.run(Bisbis10Application.class, args);
		System.out.println("bisbis10 API is ready to use !");
	}

}
