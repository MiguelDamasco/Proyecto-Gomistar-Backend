package com.gomistar.proyecto_gomistar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println("Contraseña encryptada: " + new BCryptPasswordEncoder().encode("1234"));
		SpringApplication.run(Application.class, args);
	}

}
