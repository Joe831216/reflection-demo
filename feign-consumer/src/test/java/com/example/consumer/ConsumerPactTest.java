package com.example.consumer;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "HELLO-SERVICE.ribbon.listOfServers: localhost:8888"
})
public class ConsumerPactTest {

    @Rule
    public PactProviderRuleMk2 mockProvider =
            new PactProviderRuleMk2("HELLO-SERVICE", "localhost", 8888, this);

    @Autowired
    private HelloClient helloClient;

    @Pact(provider = "HELLO-SERVICE", consumer = "FEIGN-CONSUMER")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
                .given("test state")
                .uponReceiving("ConsumerPactTest test interaction")
                    .path("/hello/")
                    .method("GET")
                .willRespondWith()
                    .status(200)
                    .body("Hello world!")
                .toPact();
    }

    @Test
    @PactVerification("HELLO-SERVICE")
    public void runTest() {
        String result = helloClient.hello();
        Assert.assertEquals(result, "Hello world!");
    }

}
