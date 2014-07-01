import com.roommateAPI.resources.Authentication;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Rodenberg
 */
@RunWith(MockitoJUnitRunner.class)
public final class AuthenticationTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @InjectMocks
    Authentication authentication;

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
}
