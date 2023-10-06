package at.htl.repository;

import at.htl.model.Drive;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

public class DriveReository {

    @Inject
    EntityManager entityManager;

    @Transactional
    public Drive addDrive(Drive drive) {
        entityManager.persist(drive);
        return drive;
    }

    @Transactional
    public void removeDrive(final long driveId) {
        final Drive drive= findByDriveId(driveId);
        final Drive car = findById(carId);
        entityManager.remove(userCar);
        entityManager.remove(car);
    }

    private Drive findByDriveId(long driveId) {
        return entityManager.find(Drive.class, driveId);
    }
}
