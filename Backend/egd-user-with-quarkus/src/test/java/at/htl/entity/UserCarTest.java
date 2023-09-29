//package at.htl.entity;
//
//import at.htl.model.Car;
//import at.htl.model.Drive;
//import at.htl.model.UserCar;
//import at.htl.model.Users;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserCarTest {
//
//    @Test
//    public void testGetId() {
//        // Arrange
//        Long id = 1L;
//        UserCar userCar = new UserCar();
//        userCar.setId(id);
//
//        // Act
//        Long actualId = userCar.getId();
//
//        // Assert
//        Assertions.assertEquals(id, actualId);
//    }
//
//    @Test
//    public void testGetUser() {
//        // Arrange
//        Users user = new Users();
//        UserCar userCar = new UserCar();
//        userCar.setUser(user);
//
//        // Act
//        Users actualUser = userCar.getUser();
//
//        // Assert
//        Assertions.assertEquals(user, actualUser);
//    }
//
//    @Test
//    public void testGetCar() {
//        // Arrange
//        Car car = new Car();
//        UserCar userCar = new UserCar();
//        userCar.setCar(car);
//
//        // Act
//        Car actualCar = userCar.getCar();
//
//        // Assert
//        Assertions.assertEquals(car, actualCar);
//    }
//
//    @Test
//    public void testGetDrives() {
//        // Arrange
//        List<Drive> drives = new ArrayList<>();
//        UserCar userCar = new UserCar();
//        userCar.setDrives(drives);
//
//        // Act
//        List<Drive> actualDrives = userCar.getDrives();
//
//        // Assert
//        Assertions.assertEquals(drives, actualDrives);
//    }
//
//    @Test
//    public void testIsAdmin() {
//        // Arrange
//        Boolean isAdmin = true;
//        UserCar userCar = new UserCar();
//        userCar.setIsAdmin(isAdmin);
//
//        // Act
//        Boolean actualIsAdmin = userCar.getIsAdmin();
//
//        // Assert
//        Assertions.assertEquals(isAdmin, actualIsAdmin);
//    }
//
//    @Test
//    public void testSetId() {
//        // Arrange
//        Long id = 2L;
//        UserCar userCar = new UserCar();
//
//        // Act
//        userCar.setId(id);
//
//        // Assert
//        Assertions.assertEquals(id, userCar.getId());
//    }
//
//    @Test
//    public void testSetUser() {
//        // Arrange
//        Users user = new Users();
//        UserCar userCar = new UserCar();
//
//        // Act
//        userCar.setUser(user);
//
//        // Assert
//        Assertions.assertEquals(user, userCar.getUser());
//    }
//
//    @Test
//    public void testSetCar() {
//        // Arrange
//        Car car = new Car();
//        UserCar userCar = new UserCar();
//
//        // Act
//        userCar.setCar(car);
//
//        // Assert
//        Assertions.assertEquals(car, userCar.getCar());
//    }
//
//    @Test
//    public void testSetDrives() {
//        // Arrange
//        List<Drive> drives = new ArrayList<>();
//        UserCar userCar = new UserCar();
//
//        // Act
//        userCar.setDrives(drives);
//
//        // Assert
//        Assertions.assertEquals(drives, userCar.getDrives());
//    }
//
//    @Test
//    public void testSetAdmin() {
//        // Arrange
//        Boolean isAdmin = false;
//        UserCar userCar = new UserCar();
//
//        // Act
//        userCar.setIsAdmin(isAdmin);
//
//        // Assert
//        Assertions.assertEquals(isAdmin, userCar.getIsAdmin());
//    }
//
//    @Test
//    public void testEquals_SameObject() {
//        // Arrange
//        UserCar userCar = new UserCar();
//
//        // Act & Assert
//        Assertions.assertEquals(userCar, userCar);
//    }
//
//    @Test
//    public void testEquals_NullObject() {
//        // Arrange
//        UserCar userCar = new UserCar();
//
//        // Act & Assert
//        Assertions.assertNotEquals(userCar, null);
//    }
//}
