import com.roommateAPI.resources.HelloWorldResource;
import com.roommateAPI.service.HelloWorldService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class)
public class HelloWorldResourceTest{

    @Mock
    HelloWorldService helloWorldService;
    @InjectMocks
    HelloWorldResource helloWorldResource = new HelloWorldResource();

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() throws Exception{

        String actual = helloWorldResource.helloWorld();

        assertEquals("Hello world!", actual);
    }

    @Test
    public void itShouldReturnAMessageFromTheService() {
        when(helloWorldService.sayHelloTwo()).thenReturn("Hello World 2!");

        String actual = helloWorldResource.helloWorldTwo();

        assertEquals("Hello World 2!", actual);
    }


}

