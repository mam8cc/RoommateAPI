package com.roommateAPI.dao;

import com.roommateAPI.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    @Autowired
    DataSource dataSource;

    public UserModel selectUserByEmail(String email) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM Users WHERE email = ?");
        statement.setString(1, email);
        ResultSet results  = statement.executeQuery();
        results.next();

        UserModel model = new UserModel();
        model.setEmail(email);
        model.setUserId(results.getLong("id"));

        return model;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
