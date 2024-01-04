package at.htl.repository;

import at.htl.model.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CarRepository {

    @Inject
    EntityManager entityManager;

    @Inject
    UserCarRepository userCarRepository;

    @Inject
    InvitationRepository invitationRepository;

    @Inject
    AccidentRepository accidentRepository;

    //CARS
    @Transactional
    public Car addCar(Car car) {
        entityManager.persist(car);
        return car;
    }

    @Transactional
    public void removeCar(final long carId) {
        final Invitation invitation = invitationRepository.findByCarId(carId);
        final List<UserCar> userCars= userCarRepository.findByCarId(carId);
        final Car car = findById(carId);

        entityManager.remove(invitation);


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


    public Car getCarWithOutId(Car car) {

        String jpql = "SELECT c FROM Car c WHERE c.name = :name AND c.licensePlate = :licensePlate";
        TypedQuery<Car> query = entityManager.createQuery(jpql, Car.class);
        query.setParameter("name", car.getName());
        query.setParameter("licensePlate", car.getLicensePlate());

        Car existingCar = query.getSingleResult();
        return existingCar; // Falls gefunden, gib das existierende Auto zurück

    }


    public void removeCurrentDriverIFUserGetsRemoved(long userId) {
        var acc =  entityManager.createQuery("SELECT i FROM Car i WHERE i.currentUser.id = :userId")
                .setParameter("userId", userId).getSingleResult();

        Car car = (Car) acc;
        car.setCurrentUser(null);
        entityManager.merge(car);
    }
}
