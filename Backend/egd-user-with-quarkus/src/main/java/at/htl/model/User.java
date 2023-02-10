package at.htl.model;

import at.htl.repository.UserRepository;
import at.htl.resource.UserResource;
import lombok.*;

import javax.inject.Inject;
import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

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
    private long id;

    private String userName;

    private String email;
    //private String telephoneNumber;

    private String password;



    @OneToMany(cascade = CascadeType.ALL)
    private List<Car> cars;





}
