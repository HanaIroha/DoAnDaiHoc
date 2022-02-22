package com.example.springcore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean(name = "javaConfig")
    public testXmlConfig createTest(){
        testXmlConfig a = new testXmlConfig();
        a.setText("java config");
        return a;
    }
}
