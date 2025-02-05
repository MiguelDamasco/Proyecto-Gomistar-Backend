package com.gomistar.proyecto_gomistar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableScheduling
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println("Contraseña encryptada: " + new BCryptPasswordEncoder().encode("1234"));
		SpringApplication.run(Application.class, args);
		System.out.println("Contraseña encryptada: " + new BCryptPasswordEncoder().encode("1234"));
	}

}
