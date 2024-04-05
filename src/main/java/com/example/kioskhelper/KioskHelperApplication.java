package com.example.kioskhelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class KioskHelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(KioskHelperApplication.class, args);
    }

}
