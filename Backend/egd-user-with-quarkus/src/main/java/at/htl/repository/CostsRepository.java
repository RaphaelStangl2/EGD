package at.htl.repository;

import at.htl.model.Car;
import at.htl.model.Costs;
import at.htl.model.UserCar;
import at.htl.model.Users;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CostsRepository {

    @Inject
    EntityManager entityManager;


    public List<Costs> findAllCostsByCarId(long carId) {
        return entityManager.createQuery("SELECT c FROM Costs c WHERE c.userCar.car.id = :carId", Costs.class)
                .setParameter("carId", carId)
                .getResultList();
    }

    public List<Costs> findCostsByUserId(long userId) {

        return entityManager.createQuery("SELECT c FROM Costs c WHERE c.userCar.user.id = :userId", Costs.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Transactional
    public Costs addCosts(Costs costs) {
        entityManager.persist(costs);
        return costs;
    }

    @Transactional
    public void removeCosts(Long costsId) {
        Costs costs = entityManager.find(Costs.class, costsId);
        entityManager.remove(costs);
    }

}
