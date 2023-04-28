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
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double consumption;

    private Double longitude;
    private Double latitude;


   /* @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
*/
}