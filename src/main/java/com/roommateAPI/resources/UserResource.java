package com.roommateAPI.resources;

import com.roommateAPI.dao.UserDao;
import com.roommateAPI.models.Registration;
import com.roommateAPI.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * A service with an endpoint that describes all registration options.
 * <p/>
 * TODO:
 * 1. Should anything besides a 200 be returned?
 */
@Path("user")
public final class UserResource {

    @Autowired
    UserDao userDao;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(final Registration post) throws SQLException {
        UserModel model = userDao.selectUserByEmail(post.getEmail());
        if (model != null) {
            //User already exists, kick back an exception.
            throw new ClientErrorException("email already registered", Response.Status.CONFLICT);
        }

        userDao.insertUser(post);
        return Response.status(201).build();

    }
}
