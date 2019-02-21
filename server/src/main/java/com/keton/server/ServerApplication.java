package com.keton.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author KentonLee
 * @date 2019/2/21
 */
@SuppressWarnings("ALL")
@SpringBootApplication
@ImportResource(locations = "${classpath:spring/spring-jdbc.xml}")
@MapperScan(basePackages = "com.kenton.model.mapper")
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}

