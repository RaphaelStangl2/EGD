package at.htl.repository;


import at.htl.model.Car;
import at.htl.model.Invitation;
import at.htl.model.UserCar;
import at.htl.model.Users;
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


    public List<UserCar> getAllUserCars() {
        return entityManager.createQuery("SELECT uc FROM UserCar uc", UserCar.class)
                .getResultList();
    }

    public long getUserCarIdByUserCar(UserCar userCar) {

        String jpql = "SELECT uc.id FROM UserCar uc WHERE uc.user = :user AND uc.car = :car AND uc.isAdmin = :isAdmin";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("user", userCar.getUser());
        query.setParameter("car", userCar.getCar());
        query.setParameter("isAdmin", userCar.getIsAdmin());

        return (Long) query.getSingleResult();
    }

}