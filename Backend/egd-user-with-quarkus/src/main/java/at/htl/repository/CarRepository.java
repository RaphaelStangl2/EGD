package at.htl.repository;

import at.htl.model.Car;
import at.htl.model.Users;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.transaction.Transactional;
import java.util.List;

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
    public Users addUser(Users user) {
        entityManager.persist(user);
        return user;
    }

    public Car findById(long id) {
        return entityManager.find(Car.class, id);
    }


    public List<Users> filterByName(String filter) {

        var users =  entityManager.createQuery("SELECT u FROM Users u where u.userName = : filter", Users.class)
                .setParameter("filter", filter).
                getResultList();

        return users;
    }
}
