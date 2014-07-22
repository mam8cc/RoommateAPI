package com.roommateAPI.service;

import com.roommateAPI.dao.AuthorizationTokenDao;
import com.roommateAPI.models.AuthorizationToken;
import com.roommateAPI.models.UserModel;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

import static java.util.UUID.randomUUID;

public class TokenService {

    @Autowired AuthorizationTokenDao authorizationTokenDao;

    public static AuthorizationToken createNewToken(Long userId) {
        DateTime expires = new DateTime();
        expires.plusMonths(1);

        AuthorizationToken token = new AuthorizationToken();
        token.setUserId(userId);
        token.setToken(randomUUID().toString());
        token.setExpirationDate(new Timestamp(expires.getMillis()));

        return token;
    }

    public AuthorizationToken getAuthorizationToken(UserModel user) {
        AuthorizationToken token;
        if (validTokenForUserExists(user.getId())) {
            token = authorizationTokenDao.selectAuthorizationTokenByUserId(user.getId());
            token.setExpirationDate(createNewExpirationTimestamp());
            authorizationTokenDao.updateTokenExpirationDate(token);
        } else {
            token = createNewToken(user.getId());
            authorizationTokenDao.insertAuthorizationToken(token);
        }

        return token;
    }

    private boolean validTokenForUserExists(Long userId) {
        AuthorizationToken token = authorizationTokenDao.selectAuthorizationTokenByUserId(userId);

        return token != null && token.getExpirationDate().getTime() < new DateTime().getMillis();
    }

    private static Timestamp createNewExpirationTimestamp() {
        return new Timestamp(new DateTime().plusMonths(1).getMillis());
    }
}
