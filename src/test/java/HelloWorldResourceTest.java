import com.roommateAPI.resources.HelloWorldResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class HelloWorldResourceTest{
//    @Override
//    protected Application configure() {
//        return new ResourceConfig(HelloWorldResource.class);
//    }
//
//    @Test
//    public void test() {
//        final String hello = target("hello").request().get(String.class);
//        assertEquals("Hello World!", hello);
//    }

    @InjectMocks
    HelloWorldResource helloWorldResource;

    @Test
    public void test() throws Exception{

        String actual = helloWorldResource.helloWorld();

        assertEquals("Hello world!", actual);
    }
}
