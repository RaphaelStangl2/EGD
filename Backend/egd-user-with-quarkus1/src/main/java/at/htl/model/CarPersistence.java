package at.htl.model;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class CarPersistence {
    @Inject
    EntityManager entityManager;

    public Car findById(long id) {
        return entityManager.find(Car.class, id);
    }
}
