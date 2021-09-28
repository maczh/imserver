package com.ququ.imserver.tags;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TagsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TagsApplication.class, args);
    }
}
