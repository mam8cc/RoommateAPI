package com.roommateAPI.config;

import com.roommateAPI.resources.Authentication;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyApplication extends ResourceConfig {

    public JerseyApplication() {
        register(Authentication.class);
    }


}