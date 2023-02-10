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

import java.util.UUID;
import javax.inject.Inject;
/*
import javax.mail.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


 */
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Path("egd/users/")
public class UserResource {

    @Inject
    UserRepository userRepository;

/*

    @POST
    public Response sendTemporaryPassword(String email) throws MessagingException {
        // Generate a temporary password
        String temporaryPassword = UUID.randomUUID().toString().substring(0, 8);

        // Create the email message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("noreply@example.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject("Temporary Password");
        message.setText("Your temporary password is: " + temporaryPassword);

        // Send the email
        Transport.send(message);

        return Response.ok("Temporary password sent to " + email).build();
    }
   */


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
        final User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            try {
                String storedPassword = user.getPassword();
                if (user != null && UserRepository.check(existingUser.getPassword(), storedPassword)) {
                    return Response.ok().build();
                } else {
                    return Response.status(Response.Status.UNAUTHORIZED).build();
                }
            } catch (NoSuchAlgorithmException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
