package at.htl.repository;


import at.htl.model.Car;
import at.htl.model.UserCar;
import at.htl.model.Users;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserCarRepository {
    @Inject
    EntityManager entityManager;



    public UserCar findById(long userCarId) {
        return entityManager.find(UserCar.class, userCarId);
    }

    @Transactional
    public UserCar addUserCar(UserCar userCar) {
        entityManager.persist(userCar);
        return userCar;
    }

    @Transactional
    public void removeUserCar(UserCar userCar) {

        long userId = userCar.getUser().getId();
        long carId = userCar.getCar().getId();

        long id =  entityManager.createQuery("SELECT uc.id FROM UserCar uc WHERE uc.user.id = :userId AND uc.car.id = :carId", Long.class)
                .setParameter("userId", userId)
                .setParameter("carId", carId)
                .getSingleResult();

        UserCar userCarToRemove = findById(id);

        entityManager.remove(userCarToRemove);
    }
}