package com.ququ.imserver.api.neteaseim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
public class NeteaseimApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeteaseimApplication.class, args);
	}
}
