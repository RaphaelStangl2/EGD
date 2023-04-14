package at.htl.resource;

import at.htl.Classes.GetUserByFilterDTO;
import at.htl.model.Car;
import at.htl.model.Users;
import at.htl.repository.CarRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

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
    @Path("registration/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCar(final Car car) {
        if (car == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final Car createdCar = carRepository.addCar(car);
        return Response.created(URI.create("/api/cars/" + createdCar.getId())).build();
    }

    @POST
    @Path("filter")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Users> getUsersByFilter( GetUserByFilterDTO username) {
        if (username == null) {
            return null;
        }
        String x = username.getName();

         List<Users> usersList = (List<Users>) carRepository.filterByName(x);
        return usersList;
    }



}
