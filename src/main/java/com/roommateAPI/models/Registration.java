package com.roommateAPI.models;

/**
 * A model that represents a registration attempt.
 */
public class Registration {

    private String email;
    private String password;

    //NOTE:  Objects that are bound to incoming HTTP requests via Jersey (see UserResource.register()) the model needs a default constructor.

    public Registration() {}

    public Registration(String email,
                        String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "EMAIL: " + email + ". PASSWORD: " + password;
    }
}
