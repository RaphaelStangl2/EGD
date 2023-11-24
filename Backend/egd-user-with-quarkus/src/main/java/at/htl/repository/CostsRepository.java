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

@ApplicationScoped
public class CostsRepository {

    @Inject
    EntityManager entityManager;


    public Costs findCostsByDriveId(long driveId) {
        return entityManager.createQuery("SELECT c FROM Costs c WHERE c.drive.id = :driveId", Costs.class)
                .setParameter("driveId", driveId)
                .getSingleResult();
    }

    public Costs findCostsByUserId(long userId) {
        TypedQuery<Costs> query = entityManager.createQuery(
                "SELECT c FROM Costs c WHERE c.drive.userCar.user.id = :userId", Costs.class);
        query.setParameter("userId", userId);
        return (Costs) query.getResultList();
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
