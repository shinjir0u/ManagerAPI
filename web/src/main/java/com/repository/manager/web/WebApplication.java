package com.repository.manager.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.repository.manager.security.SecurityConfig;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Github Repository Management API", version = "1.0", description = "API for managing github repositories"))
@SpringBootApplication(scanBasePackages = "com.repository.manager")
@EntityScan(basePackages = "com.repository.manager.persistence.entity")
@EnableJpaRepositories(basePackages = "com.repository.manager.persistence.repository")
@Import(SecurityConfig.class)
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
