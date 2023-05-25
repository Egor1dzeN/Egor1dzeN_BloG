package com.boots.controller;

public class TestClient {
    private Integer id;
    private String name;
    private String number_phone;

    public TestClient(Integer id, String name, String number_phone) {
        this.id = id;
        this.name = name;
        this.number_phone = number_phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber_phone() {
        return number_phone;
    }

    public void setNumber_phone(String number_phone) {
        this.number_phone = number_phone;
    }
}
