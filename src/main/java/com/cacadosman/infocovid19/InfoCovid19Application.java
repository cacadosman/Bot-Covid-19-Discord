package com.cacadosman.infocovid19;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InfoCovid19Application {

    public static void main(String[] args) {
        SpringApplication.run(InfoCovid19Application.class, args);
    }

}
