package com.roommateAPI.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class HttpConflictException extends WebApplicationException {

    public HttpConflictException() {

    }

    public HttpConflictException(String message) {
        super(Response.status(Response.Status.CONFLICT).entity(message).build());
    }
}
