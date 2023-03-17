package at.htl.model;

import at.htl.model.Drive;

import javax.persistence.*;

@Entity
public class Costs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Double costs;

    @ManyToOne
    private Drive drive;


}

