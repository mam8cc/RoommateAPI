package com.roommateAPI.dao;

import com.roommateAPI.models.RegistrationForm;
import com.roommateAPI.models.UserModel;

public interface UserDao {

    public void insertUser(RegistrationForm user);

    public UserModel selectUserByEmail(String email);
}
