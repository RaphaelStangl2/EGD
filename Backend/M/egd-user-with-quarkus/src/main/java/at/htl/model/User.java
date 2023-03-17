package at.htl.model;

import at.htl.repository.UserRepository;
import at.htl.resource.UserResource;
import lombok.*;

import javax.inject.Inject;
import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode



@Entity
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private Set<UserCar> userCars = new HashSet<UserCar>();


    public void addUserCar(UserCar userCar) {
        userCars.add(userCar);
        userCar.setUser(this);
    }
}
