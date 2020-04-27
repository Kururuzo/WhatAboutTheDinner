package ru.restaurant;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
                new AnnotationConfigApplicationContext("spring/spring-db.xml");

        System.out.println("Beans in context:");
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }
    }
}
