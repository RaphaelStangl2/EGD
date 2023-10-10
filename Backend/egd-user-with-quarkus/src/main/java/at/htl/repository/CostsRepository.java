package at.htl.repository;

import at.htl.model.Costs;
import at.htl.model.UserCar;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class CostsRepository {

    @Inject
    EntityManager entityManager;


    public Costs findCostsByDriveId(long driveId) {
        return entityManager.createQuery("SELECT c FROM Costs c WHERE c.drive.id = :driveId", Costs.class)
                .setParameter("driveId", driveId)
                .getSingleResult();
    }
}
