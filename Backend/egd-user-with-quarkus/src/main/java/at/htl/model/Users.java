package at.htl.model;

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
public class Users {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String email;
    private String telephoneNumber;

    private String password;

    private String resetCode;
    private String healthProblems;


    /* @OneToMany(cascade = CascadeType.ALL)
    private List<Car> cars;


    */
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @Lob
    private   byte[] image;


    }