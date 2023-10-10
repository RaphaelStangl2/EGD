package at.htl.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
    private Long id;

    @ManyToOne
    private Users userToInvite;

    @ManyToOne
    private UserCar userCar;

    //Status kann "waiting"/"agree"/"dismiss"
    private String status = "waiting";


}
