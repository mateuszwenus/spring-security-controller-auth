package com.github.mateuszwenus.spring_security_controller_auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

	@Bean
	public FooService fooService() {
		return new FooService();
	}
}
