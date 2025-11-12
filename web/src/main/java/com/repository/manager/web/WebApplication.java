package com.repository.manager.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.repository.manager.web.security.Config;

@SpringBootApplication(scanBasePackages = "com.repository.manager")
@Import(Config.class)
public class WebApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

}
