package com.evangu.rayseureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Rays-Eureka服务
 * @author Gu danpeng
 * @version 1.0.0 2018-9-9
 */
@SpringBootApplication
@EnableEurekaServer
public class RaysEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RaysEurekaApplication.class, args);
	}
}
