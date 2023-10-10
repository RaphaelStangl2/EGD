package at.htl.repository;

import at.htl.model.Costs;
import at.htl.model.Drive;
import at.htl.model.UserCar;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

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
        final Costs costs= costsRepository.findCostsByDriveId(driveId);


        final List<UserCar> userCars= userCarRepository.findByDriveId(driveId);

        for (UserCar userCar : userCars) {
            if (userCar.getDrives().contains(drive)) {
                userCar.getDrives().remove(drive);
            }
        }

        // Aktualisieren der UserCar-Objekte in der Datenbank
        for (UserCar userCar : userCars) {
            entityManager.merge(userCar);
        }


        entityManager.remove(costs);
        entityManager.remove(drive);
    }

    private Drive findByDriveId(long driveId) {
        return entityManager.find(Drive.class, driveId);
    }
}
