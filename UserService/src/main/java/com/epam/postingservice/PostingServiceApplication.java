package com.epam.postingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.epam.postingservice"})
public class PostingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostingServiceApplication.class, args);
	}
}
