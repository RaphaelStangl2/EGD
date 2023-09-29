//package at.htl.entity;
//
//
//import at.htl.model.Place;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//public class PlaceTest {
//
//    @Test
//    public void testGetId() {
//        // Arrange
//        Long id = 1L;
//        Place place = new Place();
//        place.setId(id);
//
//        // Act
//        Long actualId = place.getId();
//
//        // Assert
//        Assertions.assertEquals(id, actualId);
//    }
//
//    @Test
//    public void testGetCity() {
//        // Arrange
//        String city = "Vienna";
//        Place place = new Place();
//        place.setCity(city);
//
//        // Act
//        String actualCity = place.getCity();
//
//        // Assert
//        Assertions.assertEquals(city, actualCity);
//    }
//
//    @Test
//    public void testGetZipCode() {
//        // Arrange
//        String zipCode = "12345";
//        Place place = new Place();
//        place.setZipCode(zipCode);
//
//        // Act
//        String actualZipCode = place.getZipCode();
//
//        // Assert
//        Assertions.assertEquals(zipCode, actualZipCode);
//    }
//
//    @Test
//    public void testSetId() {
//        // Arrange
//        Long id = 2L;
//        Place place = new Place();
//
//        // Act
//        place.setId(id);
//
//        // Assert
//        Assertions.assertEquals(id, place.getId());
//    }
//
//    @Test
//    public void testSetCity() {
//        // Arrange
//        String city = "Berlin";
//        Place place = new Place();
//
//        // Act
//        place.setCity(city);
//
//        // Assert
//        Assertions.assertEquals(city, place.getCity());
//    }
//
//    @Test
//    public void testSetZipCode() {
//        // Arrange
//        String zipCode = "54321";
//        Place place = new Place();
//
//        // Act
//        place.setZipCode(zipCode);
//
//        // Assert
//        Assertions.assertEquals(zipCode, place.getZipCode());
//    }
//
//    @Test
//    public void testEquals_SameObject() {
//        // Arrange
//        Place place = new Place();
//
//        // Act & Assert
//        Assertions.assertEquals(place, place);
//    }
//
//    @Test
//    public void testEquals_NullObject() {
//        // Arrange
//        Place place = new Place();
//
//        // Act & Assert
//        Assertions.assertNotEquals(place, null);
//    }
//}
