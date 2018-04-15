package com.db.bex.dbTrainingEnroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.db.bex.interns"} )
@EnableJpaRepositories("com.db.bex.interns.dao")
@EntityScan("com.db.bex.interns.entity")
public class DbTrainingEnrollApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbTrainingEnrollApplication.class, args);
	}
}
