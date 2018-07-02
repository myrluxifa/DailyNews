package com.lvmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DailyNewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyNewsApplication.class, args);
	}
}
