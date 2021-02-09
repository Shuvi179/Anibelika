package com.orion.anibelika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AnibelikaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnibelikaApplication.class, args);
	}

}
