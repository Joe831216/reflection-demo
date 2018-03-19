package com.example.consumer;

import com.google.gson.Gson;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

@RestController
public class ReflectionDemo {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionDemo.class);

    @Autowired
    private Environment env;

    @RequestMapping(value = "/callInfo", method = RequestMethod.GET, produces = "application/json")
    public String callInfo() {
        Gson gson = new Gson();
        ArrayList<ClientInfoBean> clientInfoBeanArrayList = new ArrayList<>();
        // 取得套件下所有類別
        Reflections reflections = new Reflections("", new SubTypesScanner(false));
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        // 取得服務名稱
        logger.info("Service name: " + env.getProperty("spring.application.name"));
        for (Class c : classes) {
            // 若該類別為介面且@FeignClient不為空，則印出詳細資訊
            if (c.isInterface() && c.getAnnotation(FeignClient.class) != null) {
                ClientInfoBean clientInfoBean = new ClientInfoBean(null, null, null);
                clientInfoBean.setName(c.getName());
                logger.info("    Value of: " + c.getName());
                Annotation feignClientAnnotation = c.getAnnotation(FeignClient.class);
                Method[] methods = feignClientAnnotation.annotationType().getDeclaredMethods();
                for (Method method : methods) {
                    try {
                        Object value = new Object();
                        switch (method.getName()) {
                            case "value":
                                value = method.invoke(feignClientAnnotation, (Object[])null);
                                clientInfoBean.setValue(value.toString());
                                break;
                            case "path":
                                value = method.invoke(feignClientAnnotation, (Object[])null);
                                clientInfoBean.setPath(value.toString());
                                break;
                        }
                        logger.info("        " + method.getName() + ": " + value);
                    } catch (IllegalAccessException e) {
                        System.out.println("IllegalAccessException");
                    } catch (InvocationTargetException e) {
                        System.out.println("InvocationTargetException");
                    }
                }
                clientInfoBeanArrayList.add(clientInfoBean);
            }
        }
        CallInfoBean callInfoBean = new CallInfoBean(env.getProperty("spring.application.name"), clientInfoBeanArrayList);
        String callInfoJson = gson.toJson(callInfoBean);
        return callInfoJson;
    }
}
