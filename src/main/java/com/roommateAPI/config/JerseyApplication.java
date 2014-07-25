package com.roommateAPI.config;

import com.roommateAPI.resources.ResidenceResource;
import com.roommateAPI.resources.TokenResource;
import com.roommateAPI.resources.UserResource;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyApplication extends ResourceConfig {

    public JerseyApplication() {
        register(TokenResource.class);
        register(UserResource.class);
        register(ResidenceResource.class);
    }


}