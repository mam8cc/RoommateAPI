package com.roommateAPI.config;

import com.roommateAPI.filters.AuthorizationFilter;
import com.roommateAPI.filters.CorsFilter;
import com.roommateAPI.resources.ResidenceResource;
import com.roommateAPI.resources.StatusResource;
import com.roommateAPI.resources.TokenResource;
import com.roommateAPI.resources.UserResource;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyApplication extends ResourceConfig {

    public JerseyApplication() {
        //Resources
        register(TokenResource.class);
        register(UserResource.class);
        register(ResidenceResource.class);
        register(StatusResource.class);

        //Filters
        register(CorsFilter.class);
        register(AuthorizationFilter.class);
    }


}