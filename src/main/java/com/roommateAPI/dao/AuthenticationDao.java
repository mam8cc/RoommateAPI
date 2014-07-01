package com.roommateAPI.dao;


import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;

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
}

