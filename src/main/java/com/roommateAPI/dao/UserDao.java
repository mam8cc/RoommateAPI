package com.roommateAPI.dao;

import com.roommateAPI.models.Registration;
import com.roommateAPI.models.UserModel;

public interface UserDao {

    public void insertUser(Registration user);

    public UserModel selectUserByEmail(String email);
}
