package at.htl.repository;

import at.htl.model.Costs;
import at.htl.model.Drive;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

public class DriveRepository {

    @Inject
    EntityManager entityManager;

    @Inject
    CostsRepository costsRepository;

    @Inject
    AccidentRepository accidentRepository;

    @Inject
    UserCarRepository userCarRepository;

    @Transactional
    public Drive addDrive(Drive drive) {
        entityManager.persist(drive);
        return drive;
    }

    //costs,userCar,drive,accident davor löschen
    @Transactional
    public void removeDrive(final long driveId) {
        final Drive drive= findByDriveId(driveId);
       // final Costs costs= costsRepository.findCostsByDriveId(drive);
      //  final Drive car = findById(carId);

      //  entityManager.remove(userCar);
      //  entityManager.remove(car);
    }

    private Drive findByDriveId(long driveId) {
        return entityManager.find(Drive.class, driveId);
    }
}
