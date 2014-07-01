package com.roommateAPI.resources;

import com.lambdaworks.crypto.SCryptUtil;
import com.roommateAPI.dao.AuthenticationDao;
import com.roommateAPI.dao.AuthorizationTokenDao;
import com.roommateAPI.dao.UserDao;
import com.roommateAPI.models.AuthorizationToken;
import com.roommateAPI.models.LoginAttemptModel;
import com.roommateAPI.models.UserModel;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.sql.Timestamp;

import static java.util.UUID.randomUUID;

/**
 * Jumping off point for our authentication aspirations.
 * //TODO:
 * 2. Auth scheme of choice? (scrypt, bcrypt, PBKDF2)
 * 3. What do we return?
 * 4. Do we need to store a token on server?
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
public class Authentication {

    /**
     * Returns auth token after verifying user details.
     *
     * @param userName The name of the user to be authenticated.
     * @param password the hashed version of the password selected by the user.
     *
     *
     */


    @Autowired
    AuthenticationDao authenticationDao;

    @Autowired
    AuthorizationTokenDao authorizationTokenDao;

    @Autowired
    UserDao userDao;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@FormParam("userName") String userName, @FormParam("password") String password) {


        //TODO:
        //1. Database hookup.       DONE:  Use the authenticationDao declared as a prop.
        //2. Decide on what to return.      DONE:  Return 201 on authorization (created a token), and return said token.
        //3. do we need to do anything special for https?
        //No db yet, let's just hardcode it. winning combination should be test/test
        if ("test".equalsIgnoreCase(userName.trim())) {
            //This shouldn't be necessary once DB hookup is in place, their PW in the db should be stored as a hash.
            String passwordToMatch = SCryptUtil.scrypt("test", 16384, 8, 1);
            if (SCryptUtil.check(password, passwordToMatch)) {
                //What should be returned can be discussed.
                return "SUCCESS!";
            } else {
                throw new NotAuthorizedException("Wrong password");
            }
        }
        throw new NotFoundException();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/test")
    public Response login2(final LoginAttemptModel post) throws SQLException {
        UserModel user = userDao.selectUserByEmail(post.getEmail());

        if(post.getPassword().equals(user.getPassword())) {
            AuthorizationToken token = createNewToken(user.getId());
            authorizationTokenDao.insertAuthorizationToken(token);
            return Response.ok(authorizationTokenDao.selectAuthorizationToken(token), MediaType.APPLICATION_JSON).build();
        }
        else {
            return Response.status(401).build();
        }
    }

    private AuthorizationToken createNewToken(Long userId) {
        DateTime expires = new DateTime();
        expires.plusMonths(1);

        AuthorizationToken token = new AuthorizationToken();
        token.setUserId(userId);
        token.setToken(randomUUID().toString());
        token.setExpirationDate(new Timestamp(expires.getMillis()));

        return token;
    }
}
