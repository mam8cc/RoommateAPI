package com.roommateAPI.dao;

import com.roommateAPI.models.User;

public interface UserDao {

    public void insertUser(User user);

    public User selectUser(Integer id);

    public User selectUserByEmail(String email);
}
