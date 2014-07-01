package com.roommateAPI.dao;


import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationDao {

    @Autowired
    private DataSource dataSource;

    public String getRowCount() {
        try {
            Connection conn = dataSource.getConnection();
            ResultSet results = conn.createStatement().executeQuery("SELECT * FROM USERS");

            results.next();

            return results.getString("email");
        }
        catch(Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String fetchUser(String email) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM USERS WHERE email = ?");
        statement.setString(1, email);
        ResultSet result = statement.executeQuery();

        result.next();
        return result.getString("passwordHash");
    }

    private Connection getConnection() throws SQLException {
       return dataSource.getConnection();
    }
}

