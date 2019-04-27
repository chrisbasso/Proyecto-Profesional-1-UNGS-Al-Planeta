package com.tp.proyecto1;

import com.tp.proyecto1.model.users.User;
import com.tp.proyecto1.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Proyecto1Application {

	private static final Logger log = LoggerFactory.getLogger(Proyecto1Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Proyecto1Application.class);
	}

	@Bean
	public CommandLineRunner loadData(UserService userService) {
		return args -> {
			userService.createPrivilegeIfNotFound("READ_PRIVILEGE");
			userService.createPrivilegeIfNotFound("WRITE_PRIVILEGE");
			userService.createRoleIfNotFound("ADMIN", userService.getPrivileges());
			User userAdmin = new User("root", "root", userService.getRoles());
			userService.createUserIfNotExist(userAdmin);
		};
	}

}
