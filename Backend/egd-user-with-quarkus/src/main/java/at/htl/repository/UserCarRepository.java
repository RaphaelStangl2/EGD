package at.htl.repository;


import at.htl.model.UserCar;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserCarRepository {
    @Inject
    EntityManager entityManager;


    @Transactional
    public UserCar addUserCar(UserCar userCar) {
        entityManager.persist(userCar);
        return userCar;
    }
}