package com.roommateAPI.models;

/**
 * A model that represents a registration attempt.
 */
public class RegistrationForm {

    private String email;
    private String password;

    public RegistrationForm(String email,
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

    @Override
    public String toString() {
        return "EMAIL: " + email + ". PASSWORD: " + password;
    }
}
