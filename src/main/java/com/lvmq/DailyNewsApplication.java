package com.lvmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class DailyNewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyNewsApplication.class, args);
	}
}
