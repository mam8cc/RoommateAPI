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

        if(isNotTokenAuthenticationRequest(route, requestContext.getMethod()) && isNotUserCreationRequest(route, requestContext.getMethod())) {
            String requestToken = requestContext.getHeaderString("token");
            AuthorizationToken token = authorizationTokenDao.selectAuthorizationTokenByTokenString(requestToken);

            if (!tokenService.isTokenValid(token)) {
                requestContext.abortWith(Response.status(401).entity("Please request a token.").build());
            }
        }
    }

    private boolean isNotTokenAuthenticationRequest(String route, String method) {
        return !route.equalsIgnoreCase("/tokens") && method.equalsIgnoreCase("POST");
    }

    private boolean isNotUserCreationRequest(String route, String method) {
        return !route.equalsIgnoreCase("/users") && method.equalsIgnoreCase("POST");
    }
}
