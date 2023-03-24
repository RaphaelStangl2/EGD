package at.htl.resource;

import at.htl.Classes.Account;
import at.htl.model.Users;
import at.htl.repository.UserRepository;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.security.User;
import io.smallrye.common.annotation.Blocking;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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


@Path("egd/users/")
public class UserResource {

    @Inject
    UserRepository userRepository;


    @Inject
    Mailer mailer;

    @POST
    @Path("/forgotPassword")
    @Blocking
    public Response forgotPassword(Account account) throws NoSuchAlgorithmException, InvalidKeySpecException {


        Users user;
        try {
             user = (Users) userRepository.findByEmail(account.getEmail());
        }
        catch (Exception e){
            return Response.ok("A User with this email does not exist: " + account.getEmail()).build();
        }


        if (user!=null){

           String tempPassword = userRepository.generateTempPassword();
           user.setResetPassword(UserRepository.getSaltedHash(tempPassword));

           userRepository.update(user);

           mailer.send(Mail.withText("berdankadir12@gmail.com", "Password", "Ihr temporäres Passwort lautet: " + tempPassword));

            return Response.ok("Temporary password sent to " + account.getEmail()).build();
        }
        else{
            return Response.ok("A User with this email does not exist: " + account.getEmail()).build();
        }

    }


    @POST
    @Path("/changePassword")
    @Blocking
    public Response changePassword(Account account) throws NoSuchAlgorithmException, InvalidKeySpecException {

        Users user = (Users) userRepository.findByEmail(account.getEmail());

        if (user!=null && userRepository.check(account.getResetPassword(),user.getResetPassword())){

            user.setPassword(UserRepository.getSaltedHash(account.getNewPassword()));
            userRepository.update( user);

            mailer.send(Mail.withText("berdankadir12@gmail.com", "Password",  user.getPassword()));

            return Response.ok("Temporary password sent to " + account.getEmail()).build();
        }
        else{
            return Response.ok("A User with this email does not exist" + account.getEmail()).build();
        }

    }


    @GET
    @Path("/email")
    @Blocking
    public Response hello(){
        mailer.send(Mail.withText("berdankadir12@gmail.com", "A sivcvlcmple emkjdkfjdkfjfkdjail from quarkus", "SOSOS"));
        return Response.ok("Erfolgreich eingeloggt").build();
    }


    @POST
    @Path("registration/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(final Users user) throws NoSuchAlgorithmException, InvalidKeySpecException {



        if (user == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        try {
            Users u = userRepository.findByEmail(user.getEmail());

                return Response.status(Response.Status.NOT_FOUND).build();
        } catch ( NoResultException exception) {
            user.setPassword(UserRepository.getSaltedHash(user.getPassword()));
            final Users createdUser = userRepository.addUser(user);
            return Response.created(URI.create("/api/users/" + createdUser.getId())).build();
        }

    }

    @POST
    @Path("login/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(final Users user) {


        Users existingUser;
        try {
             existingUser = userRepository.findByEmail(user.getEmail());
        }
        catch (Exception e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }



        if (existingUser != null) {
            try {
                String storedPassword = user.getPassword();
                if (user != null && UserRepository.check(storedPassword,existingUser.getPassword())) {
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