package at.htl.model;

import at.htl.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode



@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String model;
    private Double consumption;


    @OneToMany(mappedBy = "car")
    private Set<UserCar> userCars = new HashSet<UserCar>();


    public void addUserCar(UserCar userCar) {
        userCars.add(userCar);
        userCar.setCar(this);
    }
}