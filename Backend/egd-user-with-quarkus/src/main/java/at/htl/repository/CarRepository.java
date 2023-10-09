package at.htl.repository;

import at.htl.model.Car;
import at.htl.model.UserCar;
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

    @Inject
    UserCarRepository userCarRepository;

    //CARS
    @Transactional
    public Car addCar(Car car) {
        entityManager.persist(car);
        return car;
    }

    @Transactional
    public void removeCar(final long carId) {
        final List<UserCar> userCars= userCarRepository.findByCarId(carId);
        final Car car = findById(carId);


        for (UserCar uCar : userCars)
        {
            entityManager.remove(uCar);
        }

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

    public List<Car> getAllCars() {
        return entityManager.createQuery("SELECT c FROM Car c", Car.class)
                .getResultList();
    }




    @Transactional
    public UserCar addCurrentDriver(UserCar userCar) {
        if (userCar == null || userCar.getUser() == null || userCar.getCar() == null ) {
            return null;
        }

        Users currentUser = userCar.getUser();
        Car car = userCar.getCar();


        car.setCurrentUser(currentUser);

        // Save the updated Car entity to the database
      entityManager.merge(car);

        // Optionally, you can return the updated UserCar entity
        return userCar;
    }

    @Transactional
    public UserCar removeCurrentDriver(UserCar userCar) {
        final Car car = findById(userCar.getCar().getId());
        car.setCurrentUser(null);
        entityManager.merge(car);
        return userCar;
    }



}
