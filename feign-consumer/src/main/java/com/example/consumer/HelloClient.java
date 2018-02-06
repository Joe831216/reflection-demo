package com.example.consumer;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "HELLO-SERVICE", path = "/hello")
public interface HelloClient {

    @RequestMapping(method = RequestMethod.GET, path = "/")
    String hello();

}
