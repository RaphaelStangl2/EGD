package at.htl.entity;


import at.htl.model.Place;
import at.htl.model.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UsersTest {

    @Test
    public void testGetId() {
        // Arrange
        Long id = 1L;
        Users user = new Users();
        user.setId(id);

        // Act
        Long actualId = user.getId();

        // Assert
        Assertions.assertEquals(id, actualId);
    }

    @Test
    public void testGetUserName() {
        // Arrange
        String userName = "JohnDoe";
        Users user = new Users();
        user.setUserName(userName);

        // Act
        String actualUserName = user.getUserName();

        // Assert
        Assertions.assertEquals(userName, actualUserName);
    }

    @Test
    public void testGetEmail() {
        // Arrange
        String email = "john.doe@example.com";
        Users user = new Users();
        user.setEmail(email);

        // Act
        String actualEmail = user.getEmail();

        // Assert
        Assertions.assertEquals(email, actualEmail);
    }

    @Test
    public void testGetPassword() {
        // Arrange
        String password = "password123";
        Users user = new Users();
        user.setPassword(password);

        // Act
        String actualPassword = user.getPassword();

        // Assert
        Assertions.assertEquals(password, actualPassword);
    }

    @Test
    public void testGetResetCode() {
        // Arrange
        String resetCode = "ABC123";
        Users user = new Users();
        user.setResetCode(resetCode);

        // Act
        String actualResetCode = user.getResetCode();

        // Assert
        Assertions.assertEquals(resetCode, actualResetCode);
    }

    @Test
    public void testGetPlace() {
        // Arrange
        Place place = new Place();
        Users user = new Users();
        user.setPlace(place);

        // Act
        Place actualPlace = user.getPlace();

        // Assert
        Assertions.assertEquals(place, actualPlace);
    }

    @Test
    public void testGetImage() {
        // Arrange
        byte[] image = new byte[]{1, 2, 3};
        Users user = new Users();
        user.setImage(image);

        // Act
        byte[] actualImage = user.getImage();

        // Assert
        Assertions.assertEquals(image, actualImage);
    }

    @Test
    public void testSetId() {
        // Arrange
        Long id = 2L;
        Users user = new Users();

        // Act
        user.setId(id);

        // Assert
        Assertions.assertEquals(id, user.getId());
    }

    @Test
    public void testSetUserName() {
        // Arrange
        String userName = "JaneDoe";
        Users user = new Users();

        // Act
        user.setUserName(userName);

        // Assert
        Assertions.assertEquals(userName, user.getUserName());
    }

    @Test
    public void testSetEmail() {
        // Arrange
        String email = "jane.doe@example.com";
        Users user = new Users();

        // Act
        user.setEmail(email);

        // Assert
        Assertions.assertEquals(email, user.getEmail());
    }

    @Test
    public void testSetPassword() {
        // Arrange
        String password = "password456";
        Users user = new Users();

        // Act
        user.setPassword(password);

        // Assert
        Assertions.assertEquals(password, user.getPassword());
    }

    @Test
    public void testSetResetCode() {
        // Arrange
        String resetCode = "XYZ789";
        Users user = new Users();

        // Act
        user.setResetCode(resetCode);

        // Assert
        Assertions.assertEquals(resetCode, user.getResetCode());
    }

    @Test
    public void testSetPlace() {
        // Arrange
        Place place = new Place();
        Users user = new Users();

        // Act
        user.setPlace(place);

        // Assert
        Assertions.assertEquals(place, user.getPlace());
    }

    @Test
    public void testSetImage() {
        // Arrange
        byte[] image = new byte[]{4, 5, 6};
        Users user = new Users();

        // Act
        user.setImage(image);

        // Assert
        Assertions.assertEquals(image, user.getImage());
    }

    @Test
    public void testEquals_SameObject() {
        // Arrange
        Users user = new Users();

        // Act & Assert
        Assertions.assertEquals(user, user);
    }

    @Test
    public void testEquals_NullObject() {
        // Arrange
        Users user = new Users();

        // Act & Assert
        Assertions.assertNotEquals(user, null);
    }
}
