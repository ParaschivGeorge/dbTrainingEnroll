package com.db.bex.dbTrainingEnroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.db.bex.dbTrainingEnroll"},
		excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)
})
@EnableJpaRepositories("com.db.bex.dbTrainingEnroll")
@EntityScan("com.db.bex.dbTrainingEnroll.entity")
public class DbTrainingEnrollApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbTrainingEnrollApplication.class, args);
	}
}
