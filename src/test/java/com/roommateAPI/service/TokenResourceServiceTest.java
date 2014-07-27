package com.roommateAPI.service;

import com.roommateAPI.dao.AuthorizationTokenDao;
import com.roommateAPI.models.AuthorizationToken;
import com.roommateAPI.models.User;
import com.roommateAPI.utility.UniqueIdentifierGenerator;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TokenResourceServiceTest {
    @InjectMocks TokenService tokenService = new TokenService();

    @Mock UniqueIdentifierGenerator uniqueIdentifierGenerator;
    @Mock AuthorizationTokenDao authorizationTokenDao;
    
    @Test
    public void itShouldFetchATokenFromTheDatabase() throws Exception {
        
        tokenService.getAuthorizationToken(setupUserModel());

        verify(authorizationTokenDao).selectAuthorizationTokenByUserId(anyLong());
    }

    @Test
    public void itShouldReturnAModifiedTokenIfValidTokenExists() throws Exception {
        AuthorizationToken expected = setupExistingAuthorizationToken();
        when(authorizationTokenDao.selectAuthorizationTokenByUserId(anyLong())).thenReturn(expected);

        AuthorizationToken actual = tokenService.getAuthorizationToken(setupUserModel());

        assertEquals(expected, actual);
    }

    @Test
    public void itShouldUpdateTheDatabaseIfTokenIsValid() throws Exception {
        AuthorizationToken token = setupExistingAuthorizationToken();
        when(authorizationTokenDao.selectAuthorizationTokenByUserId(anyLong())).thenReturn(token);

        tokenService.getAuthorizationToken(setupUserModel());

        verify(authorizationTokenDao).updateTokenExpirationDate(token);
    }

    @Test
    public void itShouldInsertANewTokenIfNoTokenFound() throws Exception {
        when(authorizationTokenDao.selectAuthorizationTokenByUserId(anyLong())).thenReturn(null);

        tokenService.getAuthorizationToken(setupUserModel());

        verify(authorizationTokenDao).insertAuthorizationToken(any(AuthorizationToken.class));
    }

    private User setupUserModel() {
        //Password is secret
        return new User(0l, "test@test.com", "$s0$e0801$epIxT/h6HbbwHaehFnh/bw==$7H0vsXlY8UxxyW/BWx/9GuY7jEvGjT71GFd6O4SZND0=");
    }

    private AuthorizationToken setupExistingAuthorizationToken() {
        DateTime expires = new DateTime();
        expires.plusMonths(1);

        AuthorizationToken token = new AuthorizationToken();
        token.setId(0l);
        token.setUserId(0l);
        token.setToken(randomUUID().toString());
        token.setExpirationDate(new Timestamp(expires.getMillis() - 100000));

        return token;
    }
}
