package com.roommateAPI.resources;

import com.roommateAPI.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("hello")
public class HelloWorldResource {

    @Autowired
    HelloWorldService helloWorldService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        return "Hello world!";
    }

    //This is an example test using DI/mocking
    @GET
    @Path("/two")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorldTwo() {
        return helloWorldService.sayHelloTwo();
    }
}
