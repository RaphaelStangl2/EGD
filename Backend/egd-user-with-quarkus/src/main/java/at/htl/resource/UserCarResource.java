package at.htl.resource;

import at.htl.model.UserCar;
import at.htl.repository.UserCarRepository;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;


@Path("egd/userCar/")
public class UserCarResource {
    @Inject
    UserCarRepository userCarRepository;

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUserToCar(final UserCar userCar) {
        //Car car = userCar.getCar();
        //Users user = userCar.getUser();

        UserCar createdUserCar = userCarRepository.addUserCar(userCar);
        if (createdUserCar!=null){
            return Response.created(URI.create("/api/cars/" + createdUserCar.getId())).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }
}
