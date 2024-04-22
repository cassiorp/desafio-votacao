package com.softdesign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
public class DesafioVotacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioVotacaoApplication.class, args);
	}

}
