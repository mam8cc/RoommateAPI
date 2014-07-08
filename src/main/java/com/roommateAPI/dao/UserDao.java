package com.roommateAPI.dao;

import com.roommateAPI.models.UserModel;

public interface UserDao {

    public void insertUser(UserModel user);

    public UserModel selectUserByEmail(String email);
}
