package at.htl.repository;

import at.htl.model.Costs;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class CostsRepository {

    @Inject
    EntityManager entityManager;


    private Costs findCostsByDriveId(long driveId) {
        return entityManager.find(Costs.class, driveId);
    }
}
