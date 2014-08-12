package com.roommateAPI.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/status")
public class StatusResource {

    @GET
    public Response getStatus() {
        return Response.ok("The app is running.").build();
    }
}
