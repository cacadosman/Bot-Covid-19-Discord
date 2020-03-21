package com.cacadosman.infocovid19;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class InfoCovid19Application {

    public static void main(String[] args) {
        SpringApplication.run(InfoCovid19Application.class, args);
    }

}
