package com.roommateAPI.dao;

import com.roommateAPI.models.AuthorizationToken;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;

public interface AuthorizationTokenDao {

    @Insert("INSERT INTO TOKENS (id, userId, token) VALUES (#{id},#{userId},#{token})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void insertAuthorizationToken(AuthorizationToken token);

    @Select("SELECT * FROM Tokens WHERE id = #{id}")
    AuthorizationToken selectAuthorizationToken(Long id);

    @Select("SELECT * FROM Tokens WHERE userId = #{userId}")
    AuthorizationToken selectAuthorizationTokenByUserId(Long userId);

    @Update("UPDATE Tokens SET expirationDate = #{expirationDate} WHERE id = #{id}")
    void updateTokenExpirationDate(@Param("id") Long id, @Param("expirationDate") Timestamp expirationDate);
}
