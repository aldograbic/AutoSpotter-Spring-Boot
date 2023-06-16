package com.example.AutoSpotter.classes.user;

public class BusinessUser extends User {
    private String name;
    private String web;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public BusinessUser(Long id, String email, String phoneNumber, String username, String password, String name, String web) {
        super(id, email, phoneNumber, username, password);
        this.name = name;
        this.web = web;
    }
}
