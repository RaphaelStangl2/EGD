package at.htl.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode



@Entity
public class Address {
    @Id
    @GeneratedValue
    private long id;

    private String city;
    private String zipCode;

    @OneToOne
    private User user;
}
