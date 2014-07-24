package com.roommateAPI.resources;

import com.lambdaworks.crypto.SCryptUtil;
import com.roommateAPI.dao.UserDao;
import com.roommateAPI.models.AuthorizationToken;
import com.roommateAPI.models.LoginAttemptModel;
import com.roommateAPI.models.UserModel;
import com.roommateAPI.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

/**
 * Jumping off point for our authentication aspirations.
 * //TODO:
 * 5. Do we need a filter to catch calls with certain annotations to check auth?
 * <p/>
 * <p/>
 * For some light reading, see these links
 * <p/>
 * http://security.stackexchange.com/questions/4789/most-secure-password-hash-algorithms
 * https://jersey.java.net/documentation/latest/security.html
 * http://stackoverflow.com/questions/2291323/jersey-tomcat-and-security-annotations
 * <p/>
 * For now I've done hashing using SCrypt just to see how it works but we should look into our options.
 *
 * @author Steven Rodenberg
 */
@Path("authentication")
public final class Token {

    @Autowired UserDao userDao;
    @Autowired TokenService tokenService;

    /**
     * A service to return an auth token if the user has successfully logged in or an exception to indicate a login failure.
     *
     * @param post a {@link LoginAttemptModel} containing the username and password (subject to change).
     * @return
     * @throws SQLException           an exception if there is an error requesting the user from the database.
     * @throws NotAuthorizedException an exception to alert the user that the login information provided was wrong.
     * @throws NotFoundException      an exception to indicate there is no user with the email address provided.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/login")
    public AuthorizationToken login(final LoginAttemptModel post) throws SQLException, NotAuthorizedException {
        UserModel user = userDao.selectUserByEmail(post.getEmail());

        if (user == null) {
            throw new NotFoundException();
        }

        if (SCryptUtil.check(post.getPassword(), user.getPassword())) {
            return tokenService.getAuthorizationToken(user);
        } else {
            throw new NotAuthorizedException("Not authorized.");
        }
    }
}
