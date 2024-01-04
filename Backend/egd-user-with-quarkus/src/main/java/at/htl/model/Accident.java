package at.htl.model;

import at.htl.model.Car;
import at.htl.model.Costs;
import at.htl.model.Drive;
import lombok.*;

import javax.persistence.*;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "accident")
public class Accident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_car_id")
    private UserCar userCar;


    @ManyToOne
    @JoinColumn(name = "costs_id")
    private Costs costs;


}