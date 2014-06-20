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
    public void test_success() throws Exception {
        String actual = authentication.login("test", "test");

        assertEquals("SUCCESS!", actual);
    }

    @Test
    public void test_trimUsername() throws Exception {
        String actual = authentication.login(" test ", "test");

        assertEquals("SUCCESS!", actual);
    }

    @Test
    public void test_wrongUsername() throws Exception {
        String actual = authentication.login("testt", "test");

        assertEquals("FAIL", actual);
    }

    @Test
    public void test_wrongPassword() throws Exception {
        String actual = authentication.login("test", "Test");

        assertEquals("FAIL", actual);
    }
}
