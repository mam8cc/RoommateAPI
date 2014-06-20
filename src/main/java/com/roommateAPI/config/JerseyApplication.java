package com.roommateAPI.config;

import com.roommateAPI.resources.HelloWorldResource;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyApplication extends ResourceConfig {

    public JerseyApplication() {
        register(HelloWorldResource.class);
    }
}