package com.kth.chatapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.kth.chatapp.model","com.kth.chatapp.repos"})
@EntityScan(basePackages = {"com.kth.chatapp.model", "com.kth.chatapp.repos"})
@ComponentScan(basePackages={"com.kth.chatapp.controller", "com.kth.chatapp.model","com.kth.chatapp.config","com.kth.chatapp.repos"})
public class ChattappApplication extends SpringBootServletInitializer{
	public static void main(String[] args) {
		SpringApplication.run(ChattappApplication.class, args);
	}
}
