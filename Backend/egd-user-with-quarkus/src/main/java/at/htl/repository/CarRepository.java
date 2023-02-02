package at.htl.repository;

import at.htl.model.Car;
import at.htl.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class CarRepository {

    @Inject
    EntityManager entityManager;


    //CARS
    @Transactional
    public Car addCar(Car car) {
        entityManager.persist(car);
        return car;
    }

    @Transactional
    public void removeCar(final long carId) {
        final Car car = findById(carId);
        entityManager.remove(car);
    }

    @Transactional
    public User addUser(User user) {
        entityManager.persist(user);
        return user;
    }

    public Car findById(long id) {
        return entityManager.find(Car.class, id);
    }
}
