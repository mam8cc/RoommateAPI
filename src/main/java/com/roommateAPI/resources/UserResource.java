package com.roommateAPI.resources;

import com.lambdaworks.crypto.SCryptUtil;
import com.roommateAPI.dao.UserDao;
import com.roommateAPI.exceptions.HttpConflictException;
import com.roommateAPI.models.Registration;
import com.roommateAPI.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

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

        //TODO:  Research the proper way to hash this, numbers are straight from https://github.com/wg/scrypt
        User newUser = new User(null, post.getEmail(), SCryptUtil.scrypt(post.getPassword(),16384, 8, 1));
        userDao.insertUser(newUser);

        return Response.created(buildUri(newUser.getId())).entity(newUser).build();
    }

    private URI buildUri(long id) {
        return URI.create("/users/" + id);
    }

}
