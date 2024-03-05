package at.htl.resource;

import at.htl.Classes.Account;
import at.htl.Classes.Mail;
import at.htl.model.Car;
import at.htl.model.UserCar;
import at.htl.model.Users;
import at.htl.repository.CarRepository;
import at.htl.repository.UserCarRepository;
import at.htl.repository.UserRepository;
import io.smallrye.common.annotation.Blocking;

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

    private final String emergencyEmail = "raphael.stangl.12@gmail.com";

    @POST
    @Path("/contactEmergency")
    @Blocking
    public Response contactEmergency(UserCar userCar) {


        String emergencyText = "Dies ist ein IoT-Gerät für Autos, das Unfälle erkennt. Es hat soeben einen Unfall registriert.\n\n"
                + "Details zum Unfall:\n"
                + "----------------------------------\n"
                + "Name der beteiligten Person: "+userCar.getUser().getUserName()+"\n"
                + "Ort des Unfalls:\n"
                + "  - Längengrad: "+userCar.getCar().getLongitude()+"\n"
                + "  - Breitengrad: "+userCar.getCar().getLatitude()+"\n\n"
                + "Fahrzeuginformationen:\n"
                + "  - Kennzeichen: "+userCar.getCar().getLicensePlate()+"\n\n"
                + "Zusätzliche Anmerkungen: Es ist wahrscheinlich, dass die Person bewusstlos ist.\n";

        if (userCar.getUser().getHealthProblems() != ""){
            emergencyText += "\"Gesundheitliche Probleme der Person: Keine";
            //emergencyText += "Gesundheitliche Probleme der Person: "+userCar.getUser().getHealthProblems()+"\n"
             //       + "----------------------------------\n";
        }



        //send email
        Mail newMail = new at.htl.Classes.Mail();
        Account account = new Account();
        account.setEmail(emergencyEmail);
        newMail.setMailTo(account);
        newMail.setSubject("Unfall");
        newMail.setText(emergencyText);
        userRepository.sendEmailToAcount(newMail);

        return Response.ok("This text is sent to "+account.getEmail()+": " + emergencyText).build();


    }



    @DELETE
    @Path("{userCarId}/")
    public Response removeUserCar(@PathParam("userCarId") Long userCarId) {

       userCarRepository.removeUserCar(userCarId);

        return Response.noContent().build();
    }

    @POST
    @Path("/addUserCarsList")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUserCarsList(List<UserCar> userCarsToAdd) throws NoSuchAlgorithmException, InvalidKeySpecException {

        Car newCar = carRepository.addCar(userCarsToAdd.get(0).getCar());
        int x=0;
        UserCar userCartoReturn = new UserCar();

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

            if (x==0){
                userCartoReturn=createdUserCar;
            }
        }

        return Response.ok(userCartoReturn).build();

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserCars(){
        List<UserCar> userCars = userCarRepository.getAllUserCars();

        return Response.ok(userCars).build();
    }

    @GET
    @Path("isAdmin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isAdmin(UserCar userCar){

        //UserCar ohne Id
        Boolean isAdmin = userCarRepository.isThisUserAdmin(userCar);

        return Response.ok(isAdmin).build();
    }


    @POST
    @Path("getUserCarWithOutId")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserCarWithOutId(UserCar userCar){

      long id = userCarRepository.getUserCarIdByUserCar(userCar);
      UserCar actUserCar = userCarRepository.findById(id);

        return Response.ok(actUserCar).build();
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
            return Response.ok(createdUserCar).build();

        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }


}
