package at.htl.resource;

import at.htl.model.Accident;
import at.htl.model.Costs;
import at.htl.repository.AccidentRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("egd/accident/")
public class AccidentResource {

    @Inject
    AccidentRepository accidentRepository;



    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAccident(final Accident accident) {
        if (accident == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final Accident createdAcc = accidentRepository.addAccident(accident);
        return Response.created(URI.create("/api/costs/" + createdAcc.getId())).build();
    }


    @DELETE
    @Path("{accId}/")
    public Response removeAccident(@PathParam("accId") Long accId) {
        accidentRepository.removeAccident(accId);
        return Response.noContent().build();
    }
}
