package com.roommateAPI.dao;

import com.roommateAPI.models.AuthorizationToken;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface AuthorizationTokenDao {

    @Insert("INSERT INTO TOKENS (userId, token) VALUES (#{userId},#{token})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void insertAuthorizationToken(AuthorizationToken token);


    @Select("SELECT * FROM Tokens WHERE id = #{id}")
    AuthorizationToken selectAuthorizationToken(AuthorizationToken token);

//    public long  insertAuthorizationToken(Long userId) throws SQLException {
//        String uuid = randomUUID().toString();
//        PreparedStatement statement = getConnection().prepareStatement("INSERT INTO TOKENS (userId, token) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
//        statement.setLong(1, userId);
//        statement.setString(2, uuid);
//
//        statement.executeUpdate();
//
//        ResultSet genereatedKeys = statement.getGeneratedKeys();
//        genereatedKeys.next();
//        return genereatedKeys.getLong(1);
//
//    }
//
//    public AuthorizationToken fetchTokenById(long id) throws SQLException {
//        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM Tokens WHERE id = ?");
//        statement.setLong(1, ((long) id));
//        ResultSet results  = statement.executeQuery();
//        results.next();
//
//        AuthorizationToken token = new AuthorizationToken();
//        token.setExpirationDate(results.getTimestamp("expires"));
//        token.setToken(results.getString("token"));
//        token.setUserId(results.getLong("userId"));
//
//        return token;
//    }
//
//    private Connection getConnection() throws SQLException {
//        return dataSource.getConnection();
//    }
}
