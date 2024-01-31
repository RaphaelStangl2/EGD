package at.htl.model;

import at.htl.model.Drive;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "costs")
public class Costs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Double costs;

    @ManyToOne
    @JoinColumn(name = "user_car_id")
    private UserCar userCar;

    private LocalDate date;
}