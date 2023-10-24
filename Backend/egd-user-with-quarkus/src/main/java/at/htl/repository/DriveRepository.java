package at.htl.repository;

import at.htl.model.Costs;
import at.htl.model.Drive;
import at.htl.model.UserCar;
import at.htl.model.Users;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
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


        try {
            final Costs costs= costsRepository.findCostsByDriveId(driveId);
            entityManager.remove(costs);
            entityManager.remove(drive);
        } catch (Exception e) {
            entityManager.remove(drive);

        }
    }

    private Drive findByDriveId(long driveId) {
        return entityManager.find(Drive.class, driveId);
    }
}
