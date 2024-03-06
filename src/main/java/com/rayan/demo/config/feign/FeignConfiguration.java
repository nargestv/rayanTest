package com.rayan.demo.config.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableFeignClients(basePackages = "com.rayan")
@Configuration
@EnableAsync
public class FeignConfiguration {
}
