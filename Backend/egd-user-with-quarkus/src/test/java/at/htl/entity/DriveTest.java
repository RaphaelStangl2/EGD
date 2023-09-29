//package at.htl.entity;
//
//import at.htl.model.Drive;
//import at.htl.model.UserCar;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.Date;
//
//public class DriveTest {
//
//    @Test
//    public void testGetId() {
//        // Arrange
//        Long id = 1L;
//        Drive drive = new Drive();
//        drive.setId(id);
//
//        // Act
//        Long actualId = drive.getId();
//
//        // Assert
//        Assertions.assertEquals(id, actualId);
//    }
//
//    @Test
//    public void testGetUserCar() {
//        // Arrange
//        UserCar userCar = new UserCar();
//        Drive drive = new Drive();
//        drive.setUserCar(userCar);
//
//        // Act
//        UserCar actualUserCar = drive.getUserCar();
//
//        // Assert
//        Assertions.assertEquals(userCar, actualUserCar);
//    }
//
//    @Test
//    public void testGetKilometers() {
//        // Arrange
//        Double kilometers = 100.5;
//        Drive drive = new Drive();
//        drive.setKilometers(kilometers);
//
//        // Act
//        Double actualKilometers = drive.getKilometers();
//
//        // Assert
//        Assertions.assertEquals(kilometers, actualKilometers);
//    }
//
//    @Test
//    public void testGetDate() {
//        // Arrange
//        Date date = new Date();
//        Drive drive = new Drive();
//        drive.setDate(date);
//
//        // Act
//        Date actualDate = drive.getDate();
//
//        // Assert
//        Assertions.assertEquals(date, actualDate);
//    }
//
//    @Test
//    public void testSetId() {
//        // Arrange
//        Long id = 2L;
//        Drive drive = new Drive();
//
//        // Act
//        drive.setId(id);
//
//        // Assert
//        Assertions.assertEquals(id, drive.getId());
//    }
//
//    @Test
//    public void testSetUserCar() {
//        // Arrange
//        UserCar userCar = new UserCar();
//        Drive drive = new Drive();
//
//        // Act
//        drive.setUserCar(userCar);
//
//        // Assert
//        Assertions.assertEquals(userCar, drive.getUserCar());
//    }
//
//    @Test
//    public void testSetKilometers() {
//        // Arrange
//        Double kilometers = 150.3;
//        Drive drive = new Drive();
//
//        // Act
//        drive.setKilometers(kilometers);
//
//        // Assert
//        Assertions.assertEquals(kilometers, drive.getKilometers());
//    }
//
//    @Test
//    public void testSetDate() {
//        // Arrange
//        Date date = new Date();
//        Drive drive = new Drive();
//
//        // Act
//        drive.setDate(date);
//
//        // Assert
//        Assertions.assertEquals(date, drive.getDate());
//    }
//
//    @Test
//    public void testEquals_SameObject() {
//        // Arrange
//        Drive drive = new Drive();
//
//        // Act & Assert
//        Assertions.assertEquals(drive, drive);
//    }
//
//    @Test
//    public void testEquals_NullObject() {
//        // Arrange
//        Drive drive = new Drive();
//
//        // Act & Assert
//        Assertions.assertNotEquals(drive, null);
//    }
//}
