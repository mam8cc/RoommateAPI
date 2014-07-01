package com.roommateAPI.dao;

import com.roommateAPI.models.UserModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface UserDao {

    @Insert("INSERT INTO USERS (email, password) VALUES (#{email}, #{password})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void insertUser(UserModel user);

    @Select("SELECT * FROM USERS WHERE email = #{email}")
    UserModel selectUserByEmail(String email);
}
