package at.htl.repository;

import at.htl.model.Accident;
import at.htl.model.Invitation;
import at.htl.model.UserCar;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class AccidentRepository {

    @Inject
    EntityManager entityManager;

    public Accident findById(long accidentId) {
        return entityManager.find(Accident.class, accidentId);
    }

    @Transactional
    public Accident addAccident(Accident accident) {
        entityManager.persist(accident);
        return accident;
    }

    @Transactional
    public void removeAccident(final long accidentId) {
        final Accident accident = findById(accidentId);
        entityManager.remove(accident);
    }


    public Accident findByUserCarId(long userCarId) {
        var acc =  entityManager.createQuery("SELECT i FROM Accident i WHERE i.userCar.id = :userCarId")
                .setParameter("userCarId", userCarId).getSingleResult();

        return (Accident) acc;
    }

    public Accident findByCostsId(long costsId) {
        var acc =  entityManager.createQuery("SELECT i FROM Accident i WHERE i.costs.id = :costsId")
                .setParameter("costsId", costsId).getSingleResult();

        return (Accident) acc;
    }
}
