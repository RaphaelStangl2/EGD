package at.htl;

import at.htl.model.User;
import at.htl.repository.UserRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UniTests {
    @Inject
    private UserRepository  userRepository ;


    @Test
    void addUser() {
        User user = new User();
        user.setUserName("john12");
        user.setEmail("johndoe@example.com");

        User addedUser = userRepository.addUser(user);

        assertNotNull(addedUser.getId());
        assertEquals("john12", addedUser.getUserName());
        assertEquals("johndoe@example.com", addedUser.getEmail());
    }

    @Test
    void removeUser() {
        User user = new User();
        user.setUserName("Jan2");
        user.setEmail("janedoe@example.com");

        User addedUser = userRepository.addUser(user);
        userRepository.removeUser(addedUser.getId());

        User removedUser = userRepository.findById(addedUser.getId());
        assertNull(removedUser);
    }

    @Test
    void findById() {
        User user = new User();
        user.setUserName("Johnss");
        user.setEmail("johndoe@example.com");

        User addedUser = userRepository.addUser(user);
        User foundUser = userRepository.findById(addedUser.getId());

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
}

