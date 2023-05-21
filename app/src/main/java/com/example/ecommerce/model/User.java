package com.example.ecommerce.model;

public class User {
    private String username;
    private String password;
    private String id;
    private String email;

    public User(String username, String password, String id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User() {
    }

    private String phone;

    public User(String username, String password, String id, String email, String phone) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.email = email;
        this.phone = phone;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
