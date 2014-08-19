package com.roommateAPI.resources;

import com.roommateAPI.dao.ResidenceDao;
import com.roommateAPI.models.Residence;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;

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

    @POST
    public Response createResidence(Residence newResidence) {
        residenceDao.insertResidence(newResidence);

        return Response.created(buildUri(newResidence.getId())).entity(newResidence).build();
    }


    private URI buildUri(long id) {
        return URI.create("/residences/" + id);
    }
}
