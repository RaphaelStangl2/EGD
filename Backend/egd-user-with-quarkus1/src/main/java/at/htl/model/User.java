package at.htl.model;

import lombok.*;

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
    private String telephoneNumber;

    private String password;



    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_car",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private List<Car> cars;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.password = UserService.getSaltedHash(password);
    }



}
