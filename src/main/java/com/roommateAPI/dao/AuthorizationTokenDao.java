package com.roommateAPI.dao;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;

import static java.util.UUID.randomUUID;

public class AuthorizationTokenDao {

    @Autowired
    DataSource dataSource;

    public long  insertAuthorizationToken(Long userId) throws SQLException {
        String uuid = randomUUID().toString();
        PreparedStatement statement = getConnection().prepareStatement("INSERT INTO TOKENS (userId, token) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, userId);
        statement.setString(2, uuid);

        statement.executeUpdate();

        ResultSet genereatedKeys = statement.getGeneratedKeys();
        genereatedKeys.next();
        return genereatedKeys.getLong(1);

    }

    public String fetchTokenById(long id) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM Tokens WHERE id = ?");
        statement.setLong(1, ((long) id));
        ResultSet results  = statement.executeQuery();
        results.next();

        return results.getString("token");
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
