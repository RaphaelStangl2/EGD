package at.htl.model;

import javax.persistence.*;
import java.util.List;



@Entity
@Table(name = "user_car")
public class UserCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToMany(mappedBy = "userCar")
    private List<Drive> drives;
}

