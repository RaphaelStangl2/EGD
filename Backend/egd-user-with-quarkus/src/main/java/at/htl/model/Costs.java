package at.htl.model;

import at.htl.model.Drive;
import lombok.*;

import javax.persistence.*;
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
    @JoinColumn(name = "drive_id", nullable = true)
    private Drive drive;


}