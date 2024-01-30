////package at.htl.UserCar;
////
////import at.htl.model.Car;
////import at.htl.model.UserCar;
////import at.htl.repository.UserCarRepository;
////import at.htl.repository.UserRepository;
////import org.junit.jupiter.api.Test;
////
////import javax.inject.Inject;
////
////import static io.smallrye.common.constraint.Assert.assertNotNull;
////
////public class UserCarRepositoryTest {
////
////    @Inject
////    UserCarRepository userCarRepository;
////
////
////    @Test
////    public void testAddUserCar() {
////        // Create a sample UserCar entity
////        UserCar userCar = new UserCar();
////        Car car1 = new Car();
////        car1.setName("Berdan");
////
////        userCar.setCar(car1);
////
////        UserCar addedUserCar = userCarRepository.addUserCar(userCar);
////
////        // Assert that the addedUserCar object is not null
////        assertNotNull(addedUserCar);
////
////        // Assert that the addedUserCar object is successfully persisted and has a valid ID
////        assertNotNull(addedUserCar.getId());
////    }
////
////
////}
//package at.htl.UserCar;
//
//import at.htl.model.Car;
//import at.htl.model.Users;
//import at.htl.model.UserCar;
//import at.htl.repository.UserCarRepository;
//import org.junit.jupiter.api.*;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//
//import static org.mockito.Mockito.*;
//
//public class UserCarRepositoryTest {
//
//    @InjectMocks
//    private UserCarRepository userCarRepository;
//
//    @Mock
//    private EntityManager entityManager;
//
//    @Mock
//    private TypedQuery<UserCar> mockedQuery;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testFindById() {
//        // Arrange
//        long userCarId = 1L;
//        UserCar expectedUserCar = new UserCar();
//
//        when(entityManager.find(UserCar.class, userCarId)).thenReturn(expectedUserCar);
//
//        // Act
//        UserCar actualUserCar = userCarRepository.findById(userCarId);
//
//        // Assert
//        Assertions.assertEquals(expectedUserCar, actualUserCar);
//        verify(entityManager).find(UserCar.class, userCarId);
//    }
//
//    @Test
//    public void testAddUserCar() {
//        // Arrange
//        UserCar userCar = new UserCar();
//
//        // Act
//        UserCar addedUserCar = userCarRepository.addUserCar(userCar);
//
//        // Assert
//        verify(entityManager).persist(userCar);
//        Assertions.assertEquals(userCar, addedUserCar);
//    }
//
//}
//
//
