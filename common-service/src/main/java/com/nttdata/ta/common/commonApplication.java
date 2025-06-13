package com.nttdata.ta.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("com.nttdata.ta.common")
public class commonApplication {
    public static void main(String[] args) {
        SpringApplication.run(commonApplication.class, args);
    }
}