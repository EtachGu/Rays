package com.evangu.raysauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableEurekaClient
//@EnableFeignClients
//@EnableAutoConfiguration
//@ComponentScan(basePackages = {"com.evangu.*"})
public class RaysAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(RaysAuthApplication.class, args);
	}
}
