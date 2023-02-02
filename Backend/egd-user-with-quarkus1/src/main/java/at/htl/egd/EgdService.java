package at.htl.egd;

import at.htl.model.Car;
import at.htl.model.CarPersistence;
import at.htl.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class EgdService {
    @Inject
    EntityManager entityManager;

    @Inject
    CarPersistence carPersistence;


    //USERS
    @Transactional
    public User addUser(User user) {
        entityManager.persist(user);
        return user;
    }

    @Transactional
    public void removeUser(final long reservationId) {
        final Car car = carPersistence.findById(reservationId);
        entityManager.remove(car);
    }


    //CARS
    @Transactional
    public Car addCar(Car car) {
        entityManager.persist(car);
        return car;
    }

    @Transactional
    public void removeCar(final long carId) {
        final Car car = carPersistence.findById(carId);
        entityManager.remove(car);
    }
}
