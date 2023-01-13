package at.htl.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Drive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_car_id")
    private UserCar userCar;

    private Double kilometers;
    private Date date;

    @OneToMany(mappedBy = "drive")
    private List<Costs> costs;

}
