package at.htl.repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class AccidentRepository {

    @Inject
    EntityManager entityManager;
}
