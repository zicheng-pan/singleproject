package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Hello world!
 */
@SpringBootApplication
// 扫描 mybatis 通用 mapper 所在的包
@MapperScan(basePackages = "org.example.mapper")
@ComponentScan(basePackages = {"org.example","org.n3r.idworker"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
