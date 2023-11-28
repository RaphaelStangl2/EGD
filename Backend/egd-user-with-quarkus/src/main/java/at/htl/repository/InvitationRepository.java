package at.htl.repository;

import at.htl.model.Car;
import at.htl.model.Invitation;
import at.htl.model.UserCar;
import at.htl.model.Users;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;


@ApplicationScoped
public class InvitationRepository {
    @Inject
    EntityManager entityManager;


    @Inject
    UserCarRepository userCarRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    CarRepository carRepository;

    @Transactional
    public Invitation addInv(Invitation invitation) {
        long id = userCarRepository.getUserCarIdByUserCar(invitation.getUserCar());
        Users user =userRepository.findByEmail(invitation.getUserCar().getUser().getEmail());
        invitation.getUserCar().setUser(user);


        Car car = carRepository.getCarWithOutId(invitation.getUserCar().getCar());
        invitation.getUserCar().setCar(car);

        invitation.getUserCar().setId(id);
        entityManager.persist(invitation);
        return invitation;
    }


    @Transactional
    public void removeInv(final long invId) {
        final Invitation invitation = findById(invId);
        entityManager.remove(invitation);
    }


    public Invitation findById(long id) {

        return entityManager.find(Invitation.class, id);
    }


    @Transactional
    public void updateInvation(Invitation invitation) {
        //userCar hat keine Id
        long userCarIdFromInvitar =userCarRepository.getUserCarIdByUserCar(invitation.getUserCar());
        invitation.getUserCar().setId(userCarIdFromInvitar);
        entityManager.merge(invitation);


        //Status kann "waiting"/"agree"/"dismiss"
        if(Objects.equals(invitation.getStatus(), "agree")){
            //userCar adden wenn einladung erfolgreich war
            UserCar newUserCar = new UserCar();
            newUserCar.setIsAdmin(false);
            newUserCar.setUser(invitation.getUserToInvite());
            newUserCar.setCar(invitation.getUserCar().getCar());
            userCarRepository.addUserCar(newUserCar);
        }

    }

    public List<Invitation> getAllInv() {
        return entityManager.createQuery("SELECT i FROM Invitation i", Invitation.class)
                .getResultList();
    }


    public List<Invitation> getInvitatationsForUser(Long id) {
        var invs =  entityManager.createQuery("SELECT i FROM Invitation i WHERE i.userToInvite.id = :userId")
                .setParameter("userId", id).getResultList();

        return invs;
    }

    public List<Invitation> getInvitatationsSendForUser(Long id) {
        var invs =  entityManager.createQuery("SELECT i FROM Invitation i WHERE i.userCar.user.id = :userId")
                .setParameter("userId", id).getResultList();

        return invs;
    }

    public Invitation findByCarId(long carId) {
        var invs =  entityManager.createQuery("SELECT i FROM Invitation i WHERE i.userCar.car.id = :carId")
                .setParameter("carId", carId).getSingleResult();

        return (Invitation) invs;
    }
}
