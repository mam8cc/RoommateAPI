package com.roommateAPI.filters;

import com.roommateAPI.dao.AuthorizationTokenDao;
import com.roommateAPI.models.AuthorizationToken;
import com.roommateAPI.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class AuthorizationFilter implements ContainerRequestFilter {

    @Autowired TokenService tokenService;
    @Autowired AuthorizationTokenDao authorizationTokenDao;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String route = requestContext.getUriInfo().getPath();

        //TODO:  Probably should extend this to user creation as well since it happens before authentication
        if(!route.matches("(?i).*login*")) {
            String requestToken = requestContext.getHeaderString("token");
            AuthorizationToken token = authorizationTokenDao.selectAuthorizationTokenByToken(requestToken);

            if (!tokenService.isTokenValid(token)) {
                requestContext.abortWith(Response.status(401).entity("Please request a token.").build());
            }
        }
    }
}
