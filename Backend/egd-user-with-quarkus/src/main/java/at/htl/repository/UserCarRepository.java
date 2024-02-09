package at.htl.repository;


import at.htl.model.*;
import io.vertx.ext.auth.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UserCarRepository {
    @Inject
    EntityManager entityManager;

    @Inject
    AccidentRepository accidentRepository;

    @Inject
    CostsRepository costsRepository;

    @Inject
    DriveRepository driveRepository;

    public UserCar findById(long userCarId) {
        return entityManager.find(UserCar.class, userCarId);
    }


    public List<UserCar> findByCarId(long carId) {
        return entityManager.createQuery("SELECT c FROM UserCar c WHERE c.car.id = :carId", UserCar.class)
                .setParameter("carId", carId)
                .getResultList();
    }







    public UserCar findByUserId(long userId) {
        return entityManager.find(UserCar.class, userId);
    }

    @Transactional
    public UserCar addUserCar(UserCar userCar) {
        entityManager.persist(userCar);
        return userCar;
    }

    @Transactional
    public void removeUserCar(long userCarId) {

        UserCar userCarToRemove = findById(userCarId);


        Accident accident = accidentRepository.findByUserCarId(userCarId);
        accidentRepository.removeAccident(accident.getId());


        Costs costs = costsRepository.findByUserCarId(userCarId);
        accidentRepository.removeAccident(costs.getId());

        Drive drive = driveRepository.findByUserCarId(userCarId);
        accidentRepository.removeAccident(drive.getId());


        entityManager.remove(userCarToRemove);
    }


    public List<UserCar> getAllUserCars() {
        return entityManager.createQuery("SELECT uc FROM UserCar uc", UserCar.class)
                .getResultList();
    }

    public long getUserCarIdByUserCar(UserCar userCar) {

        String jpql = "SELECT uc.id FROM UserCar uc WHERE uc.user.id = :user AND uc.car.id = :car";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("user", userCar.getUser().getId());
        query.setParameter("car", userCar.getCar().getId());
        //query.setParameter("isAdmin", userCar.getIsAdmin());

        return (Long) query.getSingleResult();
    }

    public Boolean isThisUserAdmin(UserCar userCar) {
        //UserCar ohne Admin

        long id = getUserCarIdByUserCar(userCar);
        UserCar userCarWithId = findByUserId(id);
        return userCarWithId.getIsAdmin();
    }
}