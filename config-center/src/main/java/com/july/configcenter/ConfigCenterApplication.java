package com.july.configcenter;

import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.july.configcenter.config.CustomProperties;
import com.july.configcenter.config.CustomPropertiesB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableNacosConfig
public class ConfigCenterApplication {
    @Autowired
    private CustomProperties a;
    @Autowired
    private CustomPropertiesB b;

    public static void main(String[] args) {
        SpringApplication.run(ConfigCenterApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                System.out.println(a.getAval());
                System.out.println(b.getAval() + "--" + b.getBval());
            }
        };
    }
}
