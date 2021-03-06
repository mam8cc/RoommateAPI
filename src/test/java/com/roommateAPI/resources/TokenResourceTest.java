package com.roommateAPI.resources;

import com.roommateAPI.dao.AuthorizationTokenDao;
import com.roommateAPI.dao.UserDao;
import com.roommateAPI.models.AuthorizationToken;
import com.roommateAPI.models.Login;
import com.roommateAPI.models.User;
import com.roommateAPI.service.TokenService;
import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import java.sql.Timestamp;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Steven Rodenberg
 */
@RunWith(MockitoJUnitRunner.class)
public final class TokenResourceTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @InjectMocks TokenResource tokenResource = new TokenResource();

    @Mock UserDao userDao;
    @Mock AuthorizationTokenDao authorizationTokenDao;
    @Mock TokenService tokenService;

    @Test(expected = NotAuthorizedException.class)
    public void itShouldReturnHTTPNotAuthorizedWhenAuthorizationIsBad() throws Exception {
        when(userDao.selectUserByEmail(anyString())).thenReturn(setupUserModel());

        tokenResource.login(setupBadLoginAttempt());
    }

    @Test(expected = NotFoundException.class)
    public void itShouldReturnHTTPNotFoundWhenNoUserFound() throws Exception {
        when(userDao.selectUserByEmail(anyString())).thenReturn(null);

        tokenResource.login(setupBadLoginAttempt());
    }

    @Test
    public void itShouldReturnAnAuthorizationToken() throws Exception {
        AuthorizationToken token = createAuthorizationToken();
        User user = setupUserModel();

        when(userDao.selectUserByEmail(anyString())).thenReturn(user);
        when(tokenService.getAuthorizationToken(user)).thenReturn(token);

        AuthorizationToken response = this.tokenResource.login(setupGoodLoginAttempt());

        assertEquals(token, response);
    }

    private Login setupGoodLoginAttempt() {
        Login model = new Login();
        model.setEmail("test@test.com");
        model.setPassword("secret");

        return model;
    }

    private Login setupBadLoginAttempt() {
        Login model = new Login();
        model.setEmail("test@test.com");
        model.setPassword("badPassword");

        return model;
    }

    private User setupUserModel() {
        //Password is secret
        return new User(0l, "test@test.com", "$s0$e0801$epIxT/h6HbbwHaehFnh/bw==$7H0vsXlY8UxxyW/BWx/9GuY7jEvGjT71GFd6O4SZND0=");
    }

    private AuthorizationToken createAuthorizationToken() {
        DateTime expires = new DateTime();
        expires.plusMonths(1);

        AuthorizationToken token = new AuthorizationToken();
        token.setId(0l);
        token.setUserId(0l);
        token.setToken(randomUUID().toString());
        token.setExpirationDate(new Timestamp(expires.getMillis()));

        return token;
    }

}
