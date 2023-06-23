package at.htl.User;

import at.htl.model.Users;
import at.htl.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.IOException;
//import java.security.InvalidKeySpecException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class Unit {

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction entityTransaction;

    @InjectMocks
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
    }

    @Test
    public void testAddUser() {
        // Arrange
        Users user = new Users();
        user.setUserName("JohnDoe");
        user.setEmail("john.doe@example.com");

        // Act
        Users addedUser = userRepository.addUser(user);

        // Assert
        verify(entityManager, times(1)).persist(user);
        Assertions.assertEquals(user, addedUser);
    }

    @Test
    public void testRemoveUser() {
        // Arrange
        long userId = 1L;
        Users user = new Users();
        when(entityManager.find(Users.class, userId)).thenReturn(user);

        // Act
        userRepository.removeUser(userId);

        // Assert
        verify(entityManager, times(1)).remove(user);
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        Users user = new Users();
        user.setUserName("JohnDoe");
        user.setEmail("john.doe@example.com");

        // Act
        userRepository.updateUser(user);

        // Assert
        verify(entityManager, times(1)).merge(user);
    }

    @Test
    public void testFindById() {
        // Arrange
        long userId = 1L;
        Users user = new Users();
        when(entityManager.find(Users.class, userId)).thenReturn(user);

        // Act
        Users foundUser = userRepository.findById(userId);

        // Assert
        Assertions.assertEquals(user, foundUser);
    }

    @Test
    public void testSendTemporaryPassword() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeySpecException {
        // Arrange
        String email = "john.doe@example.com";

        // Act
        String temporaryPassword = userRepository.sendTemporaryPassword(email);

        // Assert
        Assertions.assertNotNull(temporaryPassword);
        Assertions.assertNotEquals("", temporaryPassword);
    }

    @Test
    public void testGetSaltedHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Arrange
        String password = "password";

        // Act
        String saltedHash = userRepository.getSaltedHash(password);

        // Assert
        Assertions.assertNotNull(saltedHash);
        Assertions.assertNotEquals("", saltedHash);
    }

    @Test
    public void testCheck() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Arrange
        String password = "password";
        String storedHash = userRepository.getSaltedHash(password);

        // Act
        boolean result = userRepository.check(password, storedHash);

        // Assert
        Assertions.assertTrue(result);
    }
}