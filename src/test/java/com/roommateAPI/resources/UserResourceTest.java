package com.roommateAPI.resources;

import com.roommateAPI.dao.UserDao;
import com.roommateAPI.exceptions.HttpConflictException;
import com.roommateAPI.models.Registration;
import com.roommateAPI.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

    @InjectMocks UserResource userResource;

    @Mock UserDao userDao;
    
    @Test
    public void itShouldReturnAUserModel() throws Exception {
        User expected = new User();
        when(userDao.selectUser(anyInt())).thenReturn(expected);

        Response response = userResource.getUser(1);

        assertEquals(expected, response.getEntity());
    }

    @Test(expected = NotFoundException.class)
    public void itShouldThrowANotFoundException() throws Exception {
        when(userDao.selectUser(anyInt())).thenReturn(null);

        userResource.getUser(1);
    }

    @Test(expected = HttpConflictException.class)
    public void itShouldThrowAConflictException() throws Exception {
        when(userDao.selectUserByEmail(anyString())).thenReturn(new User());

        userResource.register(new Registration("", ""));
    }

    @Test
    public void itShouldCreateAUserRecord() throws Exception {
        Registration registerAttempt = new Registration("email", "password");
        User newUser = new User(null, "email", "password");

        when(userDao.selectUserByEmail(anyString())).thenReturn(null);

        Response response = userResource.register(registerAttempt);

        User actualUser = (User) response.getEntity();

        verify(userDao).insertUser(any(User.class));
        assertEquals(newUser.getEmail(), actualUser.getEmail());
        assertEquals(newUser.getPassword(), actualUser.getPassword());
    }

    @Test
    public void itShouldReturnAHTTPCreated() throws Exception {
        Registration registerAttempt = new Registration("email", "password");
        when(userDao.selectUserByEmail(anyString())).thenReturn(null);

        Response response = userResource.register(registerAttempt);

        assertEquals(201, response.getStatus());
    }
}
