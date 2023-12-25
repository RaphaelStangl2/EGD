package at.htl.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class AccidentRepository {

    @Inject
    EntityManager entityManager;


}
