package com.example.consumer;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "test-service", path = "/")
public interface TestClient {

    @RequestMapping(method = RequestMethod.GET, path = "/")
    String test();

}
