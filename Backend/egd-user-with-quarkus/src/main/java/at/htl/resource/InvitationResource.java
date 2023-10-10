package at.htl.resource;


import at.htl.model.Car;
import at.htl.model.Invitation;
import at.htl.repository.CarRepository;
import at.htl.repository.InvitationRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("egd/invitations/")

public class InvitationResource {

    @Inject
    InvitationRepository invitationRepository;


    @DELETE
    @Path("{invId}/")
    public Response removeInv(@PathParam("invId") Long carId) {
        invitationRepository.removeInv(carId);
        return Response.noContent().build();
    }



    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addInv(final Invitation invitation) {
        if (invitation == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final Invitation createdInv = invitationRepository.addInv(invitation);
        return Response.created(URI.create("/api/invitations/" + createdInv.getId())).build();
    }



    @GET
    @Path("{invId}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInvById(@PathParam("invId") Long id){
        Invitation invitation = invitationRepository.findById(id);

        if (invitation == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(invitation).build();
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInvitations(){
        List<Invitation> invitations = invitationRepository.getAllInv();

        return Response.ok(invitations).build();
    }



    @PUT
    @Path("")
    public Response updateInvitation(Invitation invitation){

        if (invitationRepository.findById(invitation.getId()) == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
            invitationRepository.updateInvation(invitation);
        }

        return Response.ok(invitation).build();
    }
}
