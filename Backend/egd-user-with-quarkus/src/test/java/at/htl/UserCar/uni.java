//package at.htl.UserCar;
//
//
//import at.htl.model.Car;
//import at.htl.model.Users;
//import at.htl.model.UserCar;
//import at.htl.model.Users;
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
//public class uni {
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
//
//}
//
