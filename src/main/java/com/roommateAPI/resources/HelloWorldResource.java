package com.roommateAPI.resources;

import com.roommateAPI.dao.AuthenticationDao;
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

    @Autowired
    AuthenticationDao authenticationDao;

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

    @GET
    @Path("/three")
    @Produces(MediaType.TEXT_PLAIN)
    public String dbTest() { return authenticationDao.getRowCount().toString(); }
}
