package at.htl.Car;

import at.htl.model.Car;
import at.htl.repository.CarRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CarRepositoryTest {

    @Inject
    CarRepository carRepository;

    @BeforeEach
    @Transactional
    void setUp() {
        Car car1 = new Car();
        car1.setName("Car 1");
        Car car2 = new Car();
        car2.setName("Car 2");
        carRepository.addCar(car1);
        carRepository.addCar(car2);
    }

    @AfterEach
    @Transactional
    void tearDown() {
        List<Car> cars = carRepository.getAllCars();
        cars.forEach(car -> carRepository.removeCar(car.getId()));
    }

    @Test
    @Order(1)
    void testFindById() {
        Car car = new Car();
        car.setName("New Car");
        carRepository.addCar(car);

        Car retrievedCar = carRepository.findById(car.getId());
        assertSame(car, retrievedCar);
    }

    @Test
    @Order(2)
    void testRemoveCar() {
        List<Car> cars = carRepository.getAllCars();
        Car carToRemove = cars.get(0);
        carRepository.removeCar(carToRemove.getId());
        Car retrievedCar = carRepository.findById(carToRemove.getId());
        assertNull(retrievedCar);
    }


    @Test
    @Order(4)
    void testUpdateCar() {
        List<Car> cars = carRepository.getAllCars();
        Car carToUpdate = cars.get(0);
        carToUpdate.setName("Updated Car");
        carRepository.updateCar(carToUpdate);
        Car retrievedCar = carRepository.findById(carToUpdate.getId());
        assertEquals("Updated Car", retrievedCar.getName());
    }
}
