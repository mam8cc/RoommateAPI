package com.roommateAPI.resources;

import com.lambdaworks.crypto.SCryptUtil;
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
public final class Authentication {

    @Autowired AuthorizationTokenDao authorizationTokenDao;
    @Autowired UserDao userDao;

    //TODO:  Extract this to a token service.  This resource knows WAYYYY too much about token logic.
    private static Timestamp createNewExpirationTimestamp() {
        return new Timestamp(new DateTime().plusMonths(1).getMillis());
    }

    private static AuthorizationToken createNewToken(Long userId) {
        DateTime expires = new DateTime();
        expires.plusMonths(1);

        AuthorizationToken token = new AuthorizationToken();
        token.setUserId(userId);
        token.setToken(randomUUID().toString());
        token.setExpirationDate(new Timestamp(expires.getMillis()));

        return token;
    }

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
    public AuthorizationToken login2(final LoginAttemptModel post) throws SQLException, NotAuthorizedException {
        UserModel user = userDao.selectUserByEmail(post.getEmail());

        if (user == null) {
            throw new NotFoundException();
        }

        if (SCryptUtil.check(post.getPassword(), user.getPassword())) {
            return getAuthorizationToken(user);
        } else {
            throw new NotAuthorizedException("Not authorized.");
        }
    }

    private AuthorizationToken getAuthorizationToken(UserModel user) {
        AuthorizationToken token;
        if (validTokenForUserExists(user.getId())) {
            //TODO: Reorganize this so that we only get the token once because we fetch it once in the IF already.
            token = authorizationTokenDao.selectAuthorizationTokenByUserId(user.getId());
            token.setExpirationDate(createNewExpirationTimestamp());
            authorizationTokenDao.updateTokenExpirationDate(token);
        } else {
            token = createNewToken(user.getId());
            authorizationTokenDao.insertAuthorizationToken(token);
        }

        return token;
    }

    private boolean validTokenForUserExists(Long userId) {
        AuthorizationToken token = authorizationTokenDao.selectAuthorizationTokenByUserId(userId);

        return token != null && token.getExpirationDate().getTime() < new DateTime().getMillis();

    }
}
