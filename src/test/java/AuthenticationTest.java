import com.roommateAPI.dao.AuthorizationTokenDao;
import com.roommateAPI.dao.UserDao;
import com.roommateAPI.models.AuthorizationToken;
import com.roommateAPI.models.LoginAttemptModel;
import com.roommateAPI.models.UserModel;
import com.roommateAPI.resources.Authentication;
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
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Steven Rodenberg
 */
@RunWith(MockitoJUnitRunner.class)
public final class AuthenticationTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @InjectMocks Authentication authentication = new Authentication();

    @Mock UserDao userDao;
    @Mock AuthorizationTokenDao authorizationTokenDao;

    @Test(expected = NotAuthorizedException.class)
    public void itShouldReturnHTTPNotAuthorizedWhenAuthorizationIsBad() throws Exception {
        when(userDao.selectUserByEmail(anyString())).thenReturn(setupUserModel());

        authentication.login2(setupBadLoginAttempt());
    }

    @Test(expected = NotFoundException.class)
    public void itShouldReturnHTTPNotFoundWhenNoUserFound() throws Exception {
        when(userDao.selectUserByEmail(anyString())).thenReturn(null);

        authentication.login2(setupBadLoginAttempt());
    }

    @Test
    public void itShouldReturnAnAuthorizationToken() throws Exception {
        AuthorizationToken token = createAuthorizationToken();

        when(userDao.selectUserByEmail(anyString())).thenReturn(setupUserModel());
        when(authorizationTokenDao.selectAuthorizationTokenByUserId(anyLong())).thenReturn(token);

        AuthorizationToken response = authentication.login2(setupGoodLoginAttempt());

        assertEquals(token, response);
    }

    private LoginAttemptModel setupGoodLoginAttempt() {
        LoginAttemptModel model = new LoginAttemptModel();
        model.setEmail("test@test.com");
        model.setPassword("secret");

        return model;
    }

    private LoginAttemptModel setupBadLoginAttempt() {
        LoginAttemptModel model = new LoginAttemptModel();
        model.setEmail("test@test.com");
        model.setPassword("badPassword");

        return model;
    }

    private UserModel setupUserModel() {
        //Password is secret
        return new UserModel(0l, "test@test.com", "$s0$e0801$epIxT/h6HbbwHaehFnh/bw==$7H0vsXlY8UxxyW/BWx/9GuY7jEvGjT71GFd6O4SZND0=");
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
