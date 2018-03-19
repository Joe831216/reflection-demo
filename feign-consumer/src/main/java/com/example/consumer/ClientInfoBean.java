package com.example.consumer;

import javax.annotation.Nullable;

public class ClientInfoBean {

    private String name;
    private String value;
    private String path;

    public ClientInfoBean(@Nullable String name, @Nullable String value, @Nullable String path) {
        this.name = name;
        this.value = value;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
