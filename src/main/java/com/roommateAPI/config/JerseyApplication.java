package com.roommateAPI.config;

import com.roommateAPI.resources.Authentication;
import com.roommateAPI.resources.Registration;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyApplication extends ResourceConfig {

    public JerseyApplication() {
        register(Authentication.class);
        register(Registration.class);
    }


}