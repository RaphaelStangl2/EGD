package at.htl.resource;

import at.htl.Classes.DateDto;
import at.htl.model.Car;
import at.htl.model.Drive;
import at.htl.repository.DriveRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

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


    @GET
    @Path("{userCarId}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDrivesByUserCarId(@PathParam("userCarId") Long userCarId){
        List<Drive> drives = driveRepository.getAllDrivesByUserId(userCarId);

        if (drives == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(drives).build();
    }

    @GET
    @Path("allDrives")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDrives() {
        List<Drive> allDrives = driveRepository.getAllDrives();

        if (allDrives == null || allDrives.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(allDrives).build();
    }


    @POST
    @Path("getDrivesByDateRange")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDrivesByDateRange(final DateDto dateDto) {
        if (dateDto == null || dateDto.getFromDate() == null || dateDto.getToDate() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid date range").build();
        }

        List<Drive> drives = driveRepository.getDrivesByDateRange(dateDto.getFromDate(), dateDto.getToDate());

        if (drives == null || drives.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(drives).build();
    }

}
