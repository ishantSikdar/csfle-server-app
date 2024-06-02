package com.ishant.csfle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class CsfleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsfleApplication.class, args);
	}

}
