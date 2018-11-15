package com.evangu.raysgate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableZuulProxy
public class RaysGateApplication {

	public static void main(String[] args) {
		SpringApplication.run(RaysGateApplication.class, args);
	}
}
