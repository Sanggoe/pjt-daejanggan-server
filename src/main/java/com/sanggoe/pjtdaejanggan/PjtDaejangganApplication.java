package com.sanggoe.pjtdaejanggan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// exclude = {SecurityAutoConfiguration.class}
@SpringBootApplication()
public class PjtDaejangganApplication {
	public static void main(String[] args) {
		SpringApplication.run(PjtDaejangganApplication.class, args);
	}
}
