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




    public Car findById(long id) {

        return entityManager.find(Car.class, id);
    }

    public List<Car> getCarsForUser(long id){
        var cars =  entityManager.createQuery("SELECT c FROM Car c WHERE c.id in (Select u.car.id from UserCar u Where u.user.id = :userId)")
                .setParameter("userId", id).getResultList();

        return cars;
    }

    @Transactional
    public void updateCar(Car car) {
        entityManager.merge(car);
    }

}
