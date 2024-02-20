package com.lamp.devops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableAsync
@EnableWebSocket
@EnableScheduling
@SpringBootApplication
public class LampOpsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LampOpsApplication.class, args);
    }
}
