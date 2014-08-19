package com.roommateAPI.service;

import com.roommateAPI.dao.AuthorizationTokenDao;
import com.roommateAPI.models.AuthorizationToken;
import com.roommateAPI.models.User;
import com.roommateAPI.utility.UniqueIdentifierGenerator;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

public class TokenService {

    @Autowired AuthorizationTokenDao authorizationTokenDao;
    @Autowired UniqueIdentifierGenerator uniqueIdentifierGenerator;

    public AuthorizationToken getAuthorizationToken(User user) {
        AuthorizationToken token = authorizationTokenDao.selectAuthorizationTokenByUserId(user.getId());
        if (isTokenValid(token)) {
            token.setExpirationDate(createNewExpirationTimestamp());
            authorizationTokenDao.updateTokenExpirationDate(token);
        } else {
            token = createNewToken(user.getId());
            authorizationTokenDao.insertAuthorizationToken(token);
        }

        return token;
    }

    private AuthorizationToken createNewToken(Long userId) {
        DateTime expires = new DateTime();
        expires.plusMonths(1);

        AuthorizationToken token = new AuthorizationToken();
        token.setUserId(userId);
        token.setToken(uniqueIdentifierGenerator.generateId());
        token.setExpirationDate(new Timestamp(expires.getMillis()));

        return token;
    }

    public boolean isTokenValid(AuthorizationToken token) {
        if(token != null) {
            return token.getExpirationDate().getTime() < new DateTime().getMillis();
        }
        else {
            return false;
        }
    }

    private static Timestamp createNewExpirationTimestamp() {
        return new Timestamp(new DateTime().plusMonths(1).getMillis());
    }
}
