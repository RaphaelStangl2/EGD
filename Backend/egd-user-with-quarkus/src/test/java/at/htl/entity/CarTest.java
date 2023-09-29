//package at.htl.entity;
//
//
//import at.htl.model.Car;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//public class CarTest {
//
//    @Test
//    public void testGetId() {
//        // Arrange
//        Long id = 1L;
//        Car car = new Car();
//        car.setId(id);
//
//        // Act
//        Long actualId = car.getId();
//
//        // Assert
//        Assertions.assertEquals(id, actualId);
//    }
//
//    @Test
//    public void testGetName() {
//        // Arrange
//        String name = "Toyota Camry";
//        Car car = new Car();
//        car.setName(name);
//
//        // Act
//        String actualName = car.getName();
//
//        // Assert
//        Assertions.assertEquals(name, actualName);
//    }
//
//    @Test
//    public void testGetConsumption() {
//        // Arrange
//        Double consumption = 8.5;
//        Car car = new Car();
//        car.setConsumption(consumption);
//
//        // Act
//        Double actualConsumption = car.getConsumption();
//
//        // Assert
//        Assertions.assertEquals(consumption, actualConsumption);
//    }
//
//    @Test
//    public void testGetLongitude() {
//        // Arrange
//        Double longitude = 15.123456;
//        Car car = new Car();
//        car.setLongitude(longitude);
//
//        // Act
//        Double actualLongitude = car.getLongitude();
//
//        // Assert
//        Assertions.assertEquals(longitude, actualLongitude);
//    }
//
//    @Test
//    public void testGetLatitude() {
//        // Arrange
//        Double latitude = 47.987654;
//        Car car = new Car();
//        car.setLatitude(latitude);
//
//        // Act
//        Double actualLatitude = car.getLatitude();
//
//        // Assert
//        Assertions.assertEquals(latitude, actualLatitude);
//    }
//
//    @Test
//    public void testSetId() {
//        // Arrange
//        Long id = 2L;
//        Car car = new Car();
//
//        // Act
//        car.setId(id);
//
//        // Assert
//        Assertions.assertEquals(id, car.getId());
//    }
//
//    @Test
//    public void testSetName() {
//        // Arrange
//        String name = "Ford Mustang";
//        Car car = new Car();
//
//        // Act
//        car.setName(name);
//
//        // Assert
//        Assertions.assertEquals(name, car.getName());
//    }
//
//    @Test
//    public void testSetConsumption() {
//        // Arrange
//        Double consumption = 9.2;
//        Car car = new Car();
//
//        // Act
//        car.setConsumption(consumption);
//
//        // Assert
//        Assertions.assertEquals(consumption, car.getConsumption());
//    }
//
//    @Test
//    public void testSetLongitude() {
//        // Arrange
//        Double longitude = 14.567890;
//        Car car = new Car();
//
//        // Act
//        car.setLongitude(longitude);
//
//        // Assert
//        Assertions.assertEquals(longitude, car.getLongitude());
//    }
//
//    @Test
//    public void testSetLatitude() {
//        // Arrange
//        Double latitude = 48.123456;
//        Car car = new Car();
//
//        // Act
//        car.setLatitude(latitude);
//
//        // Assert
//        Assertions.assertEquals(latitude, car.getLatitude());
//    }
//}
