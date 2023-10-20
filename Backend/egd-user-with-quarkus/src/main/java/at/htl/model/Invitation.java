package at.htl.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode



@Entity
@Table(name = "invitation")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Users userToInvite;

    @ManyToOne
    private UserCar userCar;

    //Status kann "waiting"/"agree"/"dismiss"
    private String status = "waiting";


}
