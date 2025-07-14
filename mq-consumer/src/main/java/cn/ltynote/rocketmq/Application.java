package cn.ltynote.rocketmq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author litianyu
 * @version 1.0.0
 * @title Application
 * @create 2025/7/14 13:19
 */
@SpringBootApplication
@MapperScan("cn.ltynote.rocketmq.mapper")
@EnableTransactionManagement
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
