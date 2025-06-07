package com.sciencefl.flynn;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(RocketMQAutoConfiguration.class)
@SpringBootApplication
public class UnionPayAdapter {
    public static void main(String[] args) {
        SpringApplication.run(UnionPayAdapter.class, args);
    }
}