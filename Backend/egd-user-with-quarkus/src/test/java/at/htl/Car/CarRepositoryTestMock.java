//package at.htl.Car;
//
//
//import at.htl.model.Car;
//import at.htl.model.Users;
//import at.htl.repository.CarRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//
//public class CarRepositoryTestMock {
//
//    @Mock
//    private EntityManager entityManager;
//
//    @Mock
//    private TypedQuery<Car> typedQuery;
//
//    @InjectMocks
//    private CarRepository carRepository;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testAddCar() {
//        // Arrange
//        Car car = new Car();
//
//        // Act
//        carRepository.addCar(car);
//
//        // Assert
//        verify(entityManager, times(1)).persist(car);
//    }
//
//    @Test
//    public void testRemoveCar() {
//        // Arrange
//        long carId = 1L;
//        Car car = new Car();
//        when(entityManager.find(Car.class, carId)).thenReturn(car);
//
//        // Act
//        carRepository.removeCar(carId);
//
//        // Assert
//        verify(entityManager, times(1)).remove(car);
//    }
//
//    @Test
//    public void testFindById() {
//        // Arrange
//        long carId = 1L;
//        Car expectedCar = new Car();
//        when(entityManager.find(Car.class, carId)).thenReturn(expectedCar);
//
//        // Act
//        Car actualCar = carRepository.findById(carId);
//
//        // Assert
//        Assertions.assertEquals(expectedCar, actualCar);
//    }
//
//    @Test
//    public void testGetCarsForUser() {
//        // Arrange
//        long userId = 1L;
//        List<Car> expectedCars = new ArrayList<>();
//        when(entityManager.createQuery(anyString(), eq(Car.class))).thenReturn(typedQuery);
//        when(typedQuery.setParameter(anyString(), eq(userId))).thenReturn(typedQuery);
//        when(typedQuery.getResultList()).thenReturn(expectedCars);
//
//        // Act
//        List<Car> actualCars = carRepository.getCarsForUser(userId);
//
//        // Assert
//        Assertions.assertEquals(expectedCars, actualCars);
//    }
//
//    @Test
//    public void testUpdateCar() {
//        // Arrange
//        Car car = new Car();
//
//        // Act
//        carRepository.updateCar(car);
//
//        // Assert
//        verify(entityManager, times(1)).merge(car);
//    }
//
//    @Test
//    public void testGetAllCars() {
//        // Arrange
//        List<Car> expectedCars = new ArrayList<>();
//        when(entityManager.createQuery(anyString(), eq(Car.class))).thenReturn(typedQuery);
//        when(typedQuery.getResultList()).thenReturn(expectedCars);
//
//        // Act
//        List<Car> actualCars = carRepository.getAllCars();
//
//        // Assert
//        Assertions.assertEquals(expectedCars, actualCars);
//    }
//}
