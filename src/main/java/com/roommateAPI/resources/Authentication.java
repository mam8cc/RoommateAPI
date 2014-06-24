package com.roommateAPI.resources;

import com.lambdaworks.crypto.SCryptUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
@Path("/authentication")
public class Authentication {

    /**
     * Returns auth token after verifying user details.
     *
     * @param userName The name of the user to be authenticated.
     * @param password the hashed version of the password selected by the user.
     */

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@FormParam("userName") String userName, @FormParam("password") String password) {
        //TODO:
        //1. Database hookup.
        //2. Decide on what to return.
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
}
