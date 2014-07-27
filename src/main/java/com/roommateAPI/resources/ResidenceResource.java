package com.roommateAPI.resources;

import com.roommateAPI.dao.ResidenceDao;
import com.roommateAPI.models.Residence;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/residences")
public class ResidenceResource {

    @Autowired ResidenceDao residenceDao;

    @GET
    @Path("/{id}")
    public Response getResidence(@PathParam("id") int id) {
        Residence residence = residenceDao.selectResidence(id);

        if(residence == null) {
            throw new NotFoundException();
        }

        return Response.ok().entity(residence).build();
    }
}
