package com.rootchen.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author LiChen
 *
 * @creat 2016/10/31
 */
@SpringBootApplication
@MapperScan("com.rootchen.mall.mapper")
@EnableSwagger2
public class MallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
    }

}
