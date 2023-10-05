package at.htl.resource;

import at.htl.model.Car;
import at.htl.model.UserCar;
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
    @Path("{carId}/")
    public Response removeCar(@PathParam("carId") Long carId) {
        carRepository.removeCar(carId);
        return Response.noContent().build();
    }



    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCar(final Car car) {
        if (car == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final Car createdCar = carRepository.addCar(car);
        return Response.created(URI.create("/api/cars/" + createdCar.getId())).build();
    }


    @GET
    @Path("carsByUserId/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCarsByUserId(@PathParam("userId") Long id){
        List<Car> cars = carRepository.getCarsForUser(id);

        if (cars == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(cars).build();
    }




    @PUT
    @Path("")
    public Response updateCar(Car car){

        if (carRepository.findById(car.getId()) == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
            carRepository.updateCar(car);
        }

        return Response.ok(car).build();
    }

    @PUT
    @Path("addCurrentDriver")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCurrentDriver(final UserCar userCar) {
        if (userCar == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final UserCar editedUserCar = carRepository.addCurrentDriver(userCar);
        return Response.created(URI.create("/api/userCar/" + editedUserCar.getId())).build();
    }

    @PUT
    @Path("removeCurrentDriver")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeCurrentDriver(final UserCar userCar) {
        if (userCar == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final UserCar removedUserCar = carRepository.removeCurrentDriver(userCar);
        return Response.created(URI.create("/api/userCar/" + removedUserCar.getId())).build();
    }
}
