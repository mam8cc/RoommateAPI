package com.roommateAPI.dao;

import com.roommateAPI.models.UserModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface UserDao {

    @Insert("INSERT INTO USERS (email, password) VALUES (#{email}, #{password})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void insertUser(UserModel user);

    @Select("SELECT * FROM USERS WHERE id = #{id}")
    UserModel selectUserByModel(UserModel user);

    @Select("SELECT * FROM USERS WHERE email = #{email}")
    UserModel selectUserByEmail(String email);

//    public UserModel selectUserByEmail(String email) throws SQLException {
//        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM Users WHERE email = ?");
//        statement.setString(1, email);
//        ResultSet results  = statement.executeQuery();
//        results.next();
//
//        UserModel model = new UserModel();
//        model.setEmail(email);
//        model.setId(results.getLong("id"));
//        model.setPassword(results.getString("password"));
//
//        return model;
//    }
}
