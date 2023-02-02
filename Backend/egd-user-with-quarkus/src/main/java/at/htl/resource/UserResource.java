package at.htl.resource;

import at.htl.model.User;
import at.htl.repository.CarRepository;
import at.htl.repository.UserRepository;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Path("egd/users/")
public class UserResource {

    @Inject
    UserRepository userRepository;

    @POST
    @Path("registration/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(final User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        user.setPassword(UserRepository.getSaltedHash(user.getPassword()));
        final User createdUser = userRepository.addUser(user);
        return Response.created(URI.create("/api/users/" + createdUser.getId())).build();
    }

    @POST
    @Path("login/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(final User user) {
        final User existingUser = userRepository.findById(user.getId());
        if (existingUser != null) {
            try {
                if (userRepository.check(user.getPassword(), existingUser.getPassword())) {
                    return Response.ok().build();
                } else {
                    return Response.status(Response.Status.UNAUTHORIZED).build();
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
