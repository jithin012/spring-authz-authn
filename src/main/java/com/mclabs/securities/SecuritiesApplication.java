package com.mclabs.securities;

import com.mclabs.securities.domain.Role;
import com.mclabs.securities.domain.User;
import com.mclabs.securities.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SecuritiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuritiesApplication.class, args);
	}
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_USER"));

			userService.saveUser(new User(null, "Jithin M Baby", "Jithin", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Jereena George", "Jereena", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Jeena M Baby", "Jeena", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Jitto M Baby", "Jitto", "1234", new ArrayList<>()));

			userService.addRoleToUser("Jithin", "ROLE_SUPER_USER");
			userService.addRoleToUser("Jereena", "ROLE_USER");
			userService.addRoleToUser("Jitto", "ROLE_MANAGER");
			userService.addRoleToUser("Jeena", "ROLE_USER");
			userService.addRoleToUser("Jereena", "ROLE_ADMIN");

		};
	}
}
					