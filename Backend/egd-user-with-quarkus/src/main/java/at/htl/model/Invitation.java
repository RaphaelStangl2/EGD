package at.htl.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
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

}
