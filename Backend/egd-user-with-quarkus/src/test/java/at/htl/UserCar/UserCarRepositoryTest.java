//package at.htl.UserCar;
//
//import at.htl.model.Car;
//import at.htl.model.UserCar;
//import at.htl.repository.UserCarRepository;
//import at.htl.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//
//import javax.inject.Inject;
//
//import static io.smallrye.common.constraint.Assert.assertNotNull;
//
//public class UserCarRepositoryTest {
//
//    @Inject
//    UserCarRepository userCarRepository;
//
//
//    @Test
//    public void testAddUserCar() {
//        // Create a sample UserCar entity
//        UserCar userCar = new UserCar();
//        Car car1 = new Car();
//        car1.setName("Berdan");
//
//        userCar.setCar(car1);
//
//        UserCar addedUserCar = userCarRepository.addUserCar(userCar);
//
//        // Assert that the addedUserCar object is not null
//        assertNotNull(addedUserCar);
//
//        // Assert that the addedUserCar object is successfully persisted and has a valid ID
//        assertNotNull(addedUserCar.getId());
//    }
//
//
//}
