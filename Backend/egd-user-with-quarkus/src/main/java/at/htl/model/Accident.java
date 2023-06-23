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
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user;


    @OneToMany
    @JoinColumn(name = "costs_id")
    private List<Costs> costs;

    @OneToOne
    @JoinColumn(name = "drive_id")
    private Drive drive;
}