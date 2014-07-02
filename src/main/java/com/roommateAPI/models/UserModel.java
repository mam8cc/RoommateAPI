package com.roommateAPI.models;

public class UserModel {
    public Long id;
    public String email;
    public String password;

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

    @Override
    public String toString() {
        return "ID: " + id + ".  EMAIL: " + email + ". PASSWORD: " + password;
    }
}
