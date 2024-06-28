package com.op.des;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.op.des.web.dao.mapper")
public class OpDestinyApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpDestinyApplication.class, args);
    }
}
