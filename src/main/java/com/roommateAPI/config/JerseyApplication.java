package com.roommateAPI.config;

import com.roommateAPI.resources.Token;
import com.roommateAPI.resources.User;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyApplication extends ResourceConfig {

    public JerseyApplication() {
        register(Token.class);
        register(User.class);
    }


}