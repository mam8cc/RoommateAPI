import com.roommateAPI.dao.AuthorizationTokenDao;
import com.roommateAPI.dao.UserDao;
import com.roommateAPI.models.AuthorizationToken;
import com.roommateAPI.models.LoginAttemptModel;
import com.roommateAPI.models.UserModel;
import com.roommateAPI.resources.Authentication;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Steven Rodenberg
 */
@RunWith(MockitoJUnitRunner.class)
public final class AuthenticationTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @InjectMocks
    Authentication authentication = new Authentication();

    @Mock
    UserDao userDao;

    @Mock
    AuthorizationTokenDao authorizationTokenDao;

    @Test
    public void itShouldReturnSuccessWithGoodCredentials() {
        String actual = authentication.login("test", "test");

        assertEquals("SUCCESS!", actual);
    }

    @Test
    public void itShouldTrimAndReturnSuccessWithGoodCredentials() throws Exception {
        String actual = authentication.login(" test ", "test");

        assertEquals("SUCCESS!", actual);
    }

    @Test
    public void itShouldReturn404WithWrongUsername() {
        exception.expect(NotFoundException.class);
        authentication.login("testt", "test");
    }

    @Test
    public void itShouldReturn401WithWrongPassword() throws Exception {
        exception.expect(NotAuthorizedException.class);
        authentication.login("test", "Test");
    }

    @Test
    public void itShouldReturnHTTPOkWhenAuthorizationIsGood() throws Exception {
        when(userDao.selectUserByEmail(anyString())).thenReturn(setupUserModel());
        Mockito.doNothing().when(authorizationTokenDao).insertAuthorizationToken(any(AuthorizationToken.class));

        Response response = authentication.login2(setupGoodLoginAttempt());

        assertEquals(200, response.getStatus());
    }

    @Test
    public void itShouldReturnHTTPNotAuthorizedWhenAuthorizationIsBad() throws Exception {
        when(userDao.selectUserByEmail(anyString())).thenReturn(setupUserModel());

        Response response = authentication.login2(setupBadLoginAttempt());

        assertEquals(401, response.getStatus());
    }

    @Test
    public void itShouldReturnHTTPNotFoundWhenNoUserFound() throws Exception {
        when(userDao.selectUserByEmail(anyString())).thenReturn(null);

        Response response = authentication.login2(setupBadLoginAttempt());

        assertEquals(404, response.getStatus());
    }

    private LoginAttemptModel setupGoodLoginAttempt() {
        LoginAttemptModel model = new LoginAttemptModel();
        model.setEmail("test@test.com");
        model.setPassword("password");

        return model;
    }

    private LoginAttemptModel setupBadLoginAttempt() {
        LoginAttemptModel model = new LoginAttemptModel();
        model.setEmail("test@test.com");
        model.setPassword("badPassword");

        return model;
    }

    private UserModel setupUserModel() {
        return new UserModel(0l, "test@test.com", "password");
    }

}
