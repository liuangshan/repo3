package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.leyou.item.mapper")
//@MapperScan("com.leyou.item.mapper")是为了扫描包

//在SpringBoot中集成MyBatis，可以在mapper接口上添加@Mapper注解，将mapper注入到Spring,但是如果每一给mapper都添加@mapper注解会很麻烦，这时可以使用@MapperScan注解来扫描包。
public class LyItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyItemApplication.class);
    }
}
