package at.htl.egd;

import at.htl.model.Car;
import at.htl.model.User;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("egd/")
public class EgdResource {
    @Inject
    EgdService egdService;

    @POST
    @Path("users/")
    public Response addUser(final User user) {
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final User createdUser = egdService.addUser(user);
        return Response.created(URI.create("/api/users/" + createdUser.getId())).build();
    }

    @DELETE
    @Path("cars/")
    public Response removeCar(final String reservationId) {
        egdService.removeCar(Long.parseLong(reservationId));
        return Response.noContent().build();
    }


    @POST
    @Path("cars/")
    public Response addCar(final Car car) {
        if (car == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final Car createdCar = egdService.addCar(car);
        return Response.created(URI.create("/api/cars/" + createdCar.getId())).build();
    }


}
