package at.htl.repository;

import at.htl.model.*;

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

     //   List<Costs> costs= entityManager.createQuery("SELECT c FROM Costs c WHERE c.userCar.user.id = :userId", Costs.class)
     //           .setParameter("userId", userId)
    //            .getResultList();


    //    return  costs;

        String queryString = "SELECT c FROM Costs c WHERE c.userCar.user.id = :userId";
        TypedQuery<Costs> query = entityManager.createQuery(queryString, Costs.class);
        query.setParameter("userId", userId);

        return query.getResultList();
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
