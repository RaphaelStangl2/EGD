package at.htl.resource;

import at.htl.model.Car;
import at.htl.model.Drive;
import at.htl.repository.DriveRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("egd/drives/")
public class DriveResource {
    @Inject
    DriveRepository driveRepository;

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDrive(final Drive drive) {
        if (drive == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final Drive createdDrive = driveRepository.addDrive(drive);
        return Response.ok(drive).build();
    }


    @DELETE
    @Path("{driveId}/")
    public Response removeDrive(@PathParam("driveId") Long driveId) {
        driveRepository.removeDrive(driveId);
        return Response.noContent().build();
    }
}
