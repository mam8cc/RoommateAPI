package com.roommateAPI.resources;

import com.roommateAPI.dao.UserDao;
import com.roommateAPI.exceptions.HttpConflictException;
import com.roommateAPI.models.Registration;
import com.roommateAPI.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public final class UserResource {

    @Autowired
    UserDao userDao;

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") Integer id) {
        User user = userDao.selectUser(id);

        if(user == null) {
            throw new NotFoundException();
        }

        return Response.ok().entity(user).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(Registration post) {
        if (userDao.selectUserByEmail(post.getEmail()) != null) {
            throw new HttpConflictException("Not found.");
        }

        User newUser = new User(null, post.getEmail(), post.getPassword());
        userDao.insertUser(newUser);

        return Response.status(201).entity(newUser).build();
    }
}
