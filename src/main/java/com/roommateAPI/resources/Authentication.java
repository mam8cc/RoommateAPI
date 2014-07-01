package com.roommateAPI.resources;

import com.roommateAPI.dao.AuthorizationTokenDao;
import com.roommateAPI.dao.UserDao;
import com.roommateAPI.models.AuthorizationToken;
import com.roommateAPI.models.LoginAttemptModel;
import com.roommateAPI.models.UserModel;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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

    @Autowired AuthorizationTokenDao authorizationTokenDao;
    @Autowired UserDao userDao;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/login")
    public AuthorizationToken login2(final LoginAttemptModel post) throws SQLException, NotAuthorizedException {
        UserModel user = userDao.selectUserByEmail(post.getEmail());

        if(user == null) {
            throw new NotFoundException();
        }

        if(post.getPassword().equals(user.getPassword())) {
            return getAuthorizationToken(user);
        }
        else {
            throw new NotAuthorizedException("Not authorized.");
        }
    }

    //TODO:  Extract this to a token service.  This resource knows WAYYYY too much about token logic.
    private AuthorizationToken getAuthorizationToken(UserModel user) {
        AuthorizationToken token;
        if(validTokenForUserExists(user.getId())) {
            authorizationTokenDao.updateTokenExpirationDate(user.getId(), createNewExpirationTimestamp());
            token = authorizationTokenDao.selectAuthorizationTokenByUserId(user.getId());
        }
        else {
            token = createNewToken(user.getId());
            authorizationTokenDao.insertAuthorizationToken(token);
        }
        return token;
    }

    private Timestamp createNewExpirationTimestamp() {
        return new Timestamp(new DateTime().plusMonths(1).getMillis());
    }

    private boolean validTokenForUserExists(Long userId) {
        AuthorizationToken token = authorizationTokenDao.selectAuthorizationTokenByUserId(userId);

        return token != null && token.getExpirationDate().getTime() < new DateTime().getMillis();

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
