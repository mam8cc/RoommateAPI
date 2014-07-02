package com.roommateAPI.dao;

import com.roommateAPI.models.AuthorizationToken;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Timestamp;

public interface AuthorizationTokenDao {

    @Insert("INSERT INTO TOKENS (id, userId, token) VALUES (#{id},#{userId},#{token})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void insertAuthorizationToken(AuthorizationToken token);

    @Select("SELECT * FROM Tokens WHERE id = #{id}")
    AuthorizationToken selectAuthorizationToken(AuthorizationToken token);

    @Select("SELECT * FROM Tokens WHERE id = #{id}")
    AuthorizationToken selectAuthorizationToken(Long id);

    @Update("UPDATE Tokens SET expirationDate = #{newExpirationDate} WHERE id = #{id}")
    AuthorizationToken updateTokenExpirationDate(Long id, Timestamp newExpirationDate);
}
