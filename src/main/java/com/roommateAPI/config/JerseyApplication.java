package com.roommateAPI.config;

import com.roommateAPI.resources.Authentication;
import com.roommateAPI.resources.User;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyApplication extends ResourceConfig {

    public JerseyApplication() {
        register(Authentication.class);
        register(User.class);
    }


}