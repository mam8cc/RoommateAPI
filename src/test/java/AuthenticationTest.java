import com.roommateAPI.resources.Authentication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Rodenberg
 */
@RunWith(MockitoJUnitRunner.class)
public final class AuthenticationTest {

    @InjectMocks
    Authentication authentication;

    @Test
    public void itShouldReturnSuccessWithGoodCredentials() throws Exception {
        String actual = authentication.login("test", "test");

        assertEquals("SUCCESS!", actual);
    }

    @Test
    public void itShouldTrimAndReturnSuccessWithGoodCredentials() throws Exception {
        String actual = authentication.login(" test ", "test");

        assertEquals("SUCCESS!", actual);
    }

    @Test
    public void itShouldReturn404WithWrongUsername() throws Exception {
        String actual = authentication.login("testt", "test");

        assertEquals("FAIL", actual);
    }

    @Test
    public void itShouldReturn401WithWrongPassword() throws Exception {
        String actual = authentication.login("test", "Test");

        assertEquals("FAIL", actual);
    }
}
