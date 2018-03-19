package com.example.consumer;

import java.util.List;

public class CallInfoBean {

    private String serviceName;
    private List<ClientInfoBean> client;

    public CallInfoBean(String serviceName, List<ClientInfoBean> client) {
        this.serviceName = serviceName;
        this.client = client;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<ClientInfoBean> getClient() {
        return client;
    }

    public void setClient(List<ClientInfoBean> client) {
        this.client = client;
    }
}
