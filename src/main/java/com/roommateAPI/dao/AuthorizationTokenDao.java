package com.roommateAPI.dao;

import com.roommateAPI.models.AuthorizationToken;

public interface AuthorizationTokenDao {

    public void insertAuthorizationToken(AuthorizationToken token);

    public AuthorizationToken selectAuthorizationTokenByUserId(Long userId);

    public void updateTokenExpirationDate(AuthorizationToken token);

    public AuthorizationToken selectAuthorizationTokenByTokenString(String tokenString);
}
