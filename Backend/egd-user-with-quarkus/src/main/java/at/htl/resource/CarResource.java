package at.htl.resource;

import at.htl.model.Car;
import at.htl.repository.CarRepository;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("egd/cars/")
public class CarResource {
    @Inject
    CarRepository carRepository;


    @DELETE
    @Path("remove/")
    public Response removeCar(final String reservationId) {
        carRepository.removeCar(Long.parseLong(reservationId));
        return Response.noContent().build();
    }


    @POST
    @Path("add/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCar(final Car car) {
        if (car == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final Car createdCar = carRepository.addCar(car);
        return Response.created(URI.create("/api/cars/" + createdCar.getId())).build();
    }


}
