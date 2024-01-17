package com.security.spring_security_course;

import com.security.spring_security_course.config.security.SecurityBeansInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class SpringSecurityCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityCourseApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(SecurityBeansInjector securityBeansInjector){
//		return args -> {
//			System.out.println(securityBeansInjector.passwordEncoder().encode("Profeta2"));
//			System.out.println(securityBeansInjector.passwordEncoder().encode("Venezogay34"));
//		};
//	}
}
