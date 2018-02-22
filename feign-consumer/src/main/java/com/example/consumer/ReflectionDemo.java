package com.example.consumer;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

@Component
public class ReflectionDemo implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionDemo.class);

    @Autowired
    private Environment env;

    @Override
    public void run(String... args) throws Exception {
        // 取得套件下所有類別
        Reflections reflections = new Reflections("", new SubTypesScanner(false));
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        // 取得服務名稱
        logger.info("Service name: " + env.getProperty("spring.application.name"));
        for (Class c : classes) {
            // 若該類別為介面且@FeignClient不為空，則印出詳細資訊
            if (c.isInterface() && c.getAnnotation(FeignClient.class) != null) {
                logger.info("    Value of: " + c.getName());
                Annotation feignClientAnnotation = c.getAnnotation(FeignClient.class);
                Method[] methods = feignClientAnnotation.annotationType().getDeclaredMethods();
                for (Method method : methods) {
                    try {
                        Object value = method.invoke(feignClientAnnotation, (Object[])null);
                        logger.info("        " + method.getName() + ": " + value);
                    } catch (IllegalAccessException e) {
                        System.out.println("IllegalAccessException");
                    } catch (InvocationTargetException e) {
                        System.out.println("InvocationTargetException");
                    }
                }
            }
        }
    }
}
