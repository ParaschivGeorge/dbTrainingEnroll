package com.db.bex.interns.dbTrainingEnroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.db.bex.interns"} )
public class DbTrainingEnrollApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbTrainingEnrollApplication.class, args);
	}
}
