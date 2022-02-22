package com.example.springcore;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
//        testXmlConfig test = (testXmlConfig) ctx.getBean("testXmlBean");
//        test.check();
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        testXmlConfig test = (testXmlConfig) ctx.getBean("javaConfig");
        test.check();
    }
}
