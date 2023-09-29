package at.htl.resource;

import at.htl.model.Car;
import at.htl.model.UserCar;
import at.htl.repository.CarRepository;
import at.htl.repository.UserCarRepository;
import at.htl.repository.UserRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;


@Path("egd/userCar/")
public class UserCarResource {
    @Inject
    UserCarRepository userCarRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    CarRepository carRepository;

    @DELETE
    @Path("/removeUserCar")
    public Response removeUserCar(UserCar userCar) {

        if (userCar==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }


       userCarRepository.removeUserCar(userCar);

        return Response.noContent().build();
    }

    @POST
    @Path("/addUserCarsList")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUserCarsList(List<UserCar> userCarsToAdd) throws NoSuchAlgorithmException, InvalidKeySpecException {

        Car newCar = carRepository.addCar(userCarsToAdd.get(0).getCar());

        for (UserCar userCar:userCarsToAdd ) {
            userCar.setCar(newCar);
            if (userCar.getUser() == null || userCar.getCar()== null ) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }

            if (userCar.getUser().getId() == null){
                userCar.getUser().setPassword(UserRepository.getSaltedHash(userCar.getUser().getPassword()));
                userRepository.addUser(userCar.getUser());
            }
            else if (userRepository.findById(userCar.getUser().getId()) == null){
                userCar.getUser().setPassword(UserRepository.getSaltedHash(userCar.getUser().getPassword()));
                userRepository.addUser(userCar.getUser());
            }

            UserCar createdUserCar = userCarRepository.addUserCar(userCar);
            if (createdUserCar==null){
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }

        return Response.created(URI.create("/api/cars/")).build();

    }



    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUserToCar(final UserCar userCar) {

        if (userCar.getUser() == null || userCar.getCar()== null ) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        if (userCar.getUser().getId() == null){
            userRepository.addUser(userCar.getUser());
        }
        else if (userRepository.findById(userCar.getUser().getId()) == null){
            userRepository.addUser(userCar.getUser());
        }
        if (userCar.getCar().getId() == null){
            carRepository.addCar(userCar.getCar());
        }
        else if (carRepository.findById(userCar.getCar().getId()) == null){
            carRepository.addCar(userCar.getCar());
        }

        UserCar createdUserCar = userCarRepository.addUserCar(userCar);
        if (createdUserCar!=null){
            return Response.created(URI.create("/api/cars/" + createdUserCar.getId())).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


}
