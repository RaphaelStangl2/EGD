package at.htl.User;
import at.htl.model.Users;
import at.htl.repository.UserRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;


import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {

    @Inject
    UserRepository userRepository;

    //static Users userTemp;

    @Test
     void addUser1(){
        Users user = new Users();
        user.setUserName("Berdan");
        user.setEmail("berdankadir12@gmail.com");
        user.setPassword("123");

        assertSame(user.getUserName(), "Berdan");
    }

    @Test
    void addUser2(){
        Users user = new Users();
        user.setUserName("Berdan");
        user.setEmail("berdankadir12@gmail.com");
        user.setPassword("123");

        assertSame(user.getEmail(), "berdankadir12@gmail.com");
    }


}