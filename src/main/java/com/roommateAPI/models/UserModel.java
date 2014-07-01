package com.roommateAPI.models;

public class UserModel {
    public Long id;
    public String email;
    public String password;

    public UserModel() {

    }

    public UserModel(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ID: " + id + ".  EMAIL: " + email + ". PASSWORD: " + password;
    }
}
