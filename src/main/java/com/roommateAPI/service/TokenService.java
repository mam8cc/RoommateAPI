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

        if(isTokenValid(token)) {
            token.setExpirationDate(createNewExpirationTimestamp());
            authorizationTokenDao.updateTokenExpirationDate(token);
        }
        else {
            authorizationTokenDao.deleteTokensForUser(user.getId());
            token = createNewToken(user.getId());
            authorizationTokenDao.insertAuthorizationToken(token);
        }

        return token;
    }

    private AuthorizationToken createNewToken(Long userId) {
        AuthorizationToken token = new AuthorizationToken();
        token.setUserId(userId);
        token.setToken(uniqueIdentifierGenerator.generateId());
        token.setExpirationDate(createNewExpirationTimestamp());

        return token;
    }

    public boolean isTokenValid(AuthorizationToken token) {
        if(token == null) {
            return false;
        }

        Timestamp expiration = new Timestamp(token.getExpirationDate().getTime());
        Timestamp now = new Timestamp(new DateTime().getMillis());

        return expiration.after(now);
    }

    private static Timestamp createNewExpirationTimestamp() {
        DateTime expires = new DateTime();
        expires = expires.plusMonths(1);

        return new Timestamp(expires.getMillis());
    }
}
