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
import javax.persistence.TypedQuery;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserTestMock {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Users> query;

    @InjectMocks
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUser() {
        // Arrange
        Users user = new Users();

        // Act
        Users result = userRepository.addUser(user);

        // Assert
        Assertions.assertEquals(user, result);
        verify(entityManager, times(1)).persist(user);
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
        Users result = userRepository.findById(userId);

        // Assert
        Assertions.assertEquals(user, result);
    }

    @Test
    public void testSendTemporaryPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Arrange
        String email = "test@example.com";
        String expectedTemporaryPassword = "tempPassword";
        when(userRepository.getSaltedHash(anyString())).thenReturn(expectedTemporaryPassword);

        // Act
        String result = userRepository.sendTemporaryPassword(email);

        // Assert
        Assertions.assertEquals(expectedTemporaryPassword, result);
        verify(userRepository, times(1)).getSaltedHash(anyString());
    }

    @Test
    public void testGetSaltedHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Arrange
        String password = "password";

        // Act
        String result = userRepository.getSaltedHash(password);

        // Assert
        Assertions.assertNotNull(result);
    }





    @Test
    public void testFilterByName() {
        // Arrange
        String filter = "John";
        List<Users> usersList = new ArrayList<>();
        when(entityManager.createQuery(anyString(), eq(Users.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(usersList);

        // Act
        List<Users> result = userRepository.filterByName(filter);

        // Assert
        Assertions.assertEquals(usersList, result);
        verify(entityManager, times(1)).createQuery(anyString(), eq(Users.class));
        verify(query, times(1)).setParameter(anyString(), anyString());
        verify(query, times(1)).getResultList();
    }

    @Test
    public void testFindByEmail() {
        // Arrange
        String email = "test@example.com";
        Users user = new Users();
        when(entityManager.createQuery(anyString(), eq(Users.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(user);

        // Act
        Users result = userRepository.findByEmail(email);

        // Assert
        Assertions.assertEquals(user, result);
        verify(entityManager, times(1)).createQuery(anyString(), eq(Users.class));
        verify(query, times(1)).setParameter(anyString(), anyString());
        verify(query, times(1)).getSingleResult();
    }

    @Test
    public void testGetCarUsersById() {
        // Arrange
        long carId = 1L;
        List<Users> usersList = new ArrayList<>();
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyLong())).thenReturn(query);
        when(query.getResultList()).thenReturn(usersList);

        // Act
        List<Users> result = userRepository.getCarUsersById(carId);

        // Assert
        Assertions.assertEquals(usersList, result);
        verify(entityManager, times(1)).createQuery(anyString());
        verify(query, times(1)).setParameter(anyString(), anyLong());
        verify(query, times(1)).getResultList();
    }

    @Test
    public void testUpdate() {
        // Arrange
        Users user = new Users();

        // Act
        userRepository.update(user);

        // Assert
        verify(entityManager, times(1)).merge(user);
    }

    @Test
    public void testGenerateTempPassword() {
        // Act
        String result = userRepository.generateTempPassword();

        // Assert
        Assertions.assertNotNull(result);
    }


}
