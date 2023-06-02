/*package at.htl;

import at.htl.model.Car;
import at.htl.model.Users;
import at.htl.repository.CarRepository;
import at.htl.repository.UserRepository;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import static org.junit.jupiter.api.Assertions.*;


import at.htl.model.Car;
import at.htl.model.Users;
import at.htl.repository.CarRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
@QuarkusTest
class UniTests {

    //@Mock
    @Inject

    private EntityManager entityManager;
    @InjectMocks
     //@Inject
    private CarRepository carRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCar() {
        // Arrange
        long carId = 1L;
        Car car = new Car();
        car.setId(carId);
        car.setName("Benzema");
        Mockito.doNothing().when(entityManager).persist(car);

        // Act
        Car addedCar = carRepository.addCar(car);

        // Assert
        Mockito.verify(entityManager).persist(car);
        assertEquals(car, addedCar);
    }


    @Test
    public void testFindById() {
        // Arrange
        long carId = 1L;
        Car expected = new Car();
        when(entityManager.find(Car.class, carId)).thenReturn(expected);

        // Act
        Car result = carRepository.findById(carId);

        // Assert
        assertEquals(expected, result);
        verify(entityManager, times(1)).find(Car.class, carId);
    }

    @Test
    public void testGetCarsForUser() {
        // Arrange
        long userId = 1L;
        List<Car> expected = List.of(new Car(), new Car());
        TypedQuery<Car> query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Car.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(expected);

        // Act
        List<Car> result = carRepository.getCarsForUser(userId);

        // Assert
        assertEquals(expected, result);
        verify(entityManager, times(1)).createQuery(anyString(), eq(Car.class));
        verify(query, times(1)).setParameter("userId", userId);
        verify(query, times(1)).getResultList();
    }

    @Test
    public void testUpdateCar() {
        // Arrange
        Car car = new Car();
        car.setName("Benzema");

        // Act
        carRepository.updateCar(car);

        // Assert
        verify(entityManager, times(1)).merge(car);
    }



  /* @Inject
    private UserRepository userRepository;

    @Test
    void addUser() {
        Users user = new Users();
        user.setUserName("john12");
        user.setEmail("johndoe@example.com");

        Users addedUser = userRepository.addUser(user);

        assertNotNull(addedUser.getId());
        assertEquals("john12", addedUser.getUserName());
        assertEquals("johndoe@example.com", addedUser.getEmail());
    }

    @Test
    void removeUser() {
        Users user = new Users();
        user.setUserName("Jan2");
        user.setEmail("janedoe@example.com");

        Users addedUser = userRepository.addUser(user);
        userRepository.removeUser(addedUser.getId());

        Users removedUser = userRepository.findById(addedUser.getId());
        assertNull(removedUser);
    }

    @Test
    void findById() {
        Users user = new Users();
        user.setUserName("Johnss");
        user.setEmail("johndoe@example.com");

        Users addedUser = userRepository.addUser(user);
        Users foundUser = userRepository.findById(addedUser.getId());

        assertNotNull(foundUser);
        assertEquals(addedUser.getId(), foundUser.getId());
        assertEquals("Johnss", foundUser.getUserName());
        assertEquals("johndoe@example.com", foundUser.getEmail());
    }

    @Test
    void sendTemporaryPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String temporaryPassword = userRepository.sendTemporaryPassword("johndoe@example.com");

        assertNotNull(temporaryPassword);
        assertTrue(temporaryPassword.contains(":"));
    }

    @Test
    void getSaltedHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "password";
        String saltedHash = UserRepository.getSaltedHash(password);

        assertNotNull(saltedHash);
        assertTrue(saltedHash.contains(":"));
    }

    @Test
    void check() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "password";
        String storedPassword = UserRepository.getSaltedHash(password);

        boolean result = UserRepository.check(password, storedPassword);

        assertTrue(result);
    }
    */
//}



