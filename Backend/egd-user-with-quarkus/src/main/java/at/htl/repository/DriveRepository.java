package at.htl.repository;

import at.htl.model.Costs;
import at.htl.model.Drive;
import at.htl.model.UserCar;
import at.htl.model.Users;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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


    public List<Drive> getAllDrivesByUserId(long userCarId) {
        String queryString = "SELECT d FROM Drive d WHERE d.userCar.id = :userCarId";
        TypedQuery<Drive> query = entityManager.createQuery(queryString, Drive.class);
        query.setParameter("userCarId", userCarId);

        return query.getResultList();
    }


   // getAllDrivesByCar;    (Gesamtübersicht für alle die dieses Auto haben)
    public List<Drive> getAllDrivesByCar(long carId){
        String queryString = "SELECT d FROM Drive d WHERE d.userCar.car.id = :carId";
        TypedQuery<Drive> query = entityManager.createQuery(queryString, Drive.class);
        query.setParameter("carId", carId);

        return query.getResultList();
    }

    private Drive findByDriveId(long driveId) {
        return entityManager.find(Drive.class, driveId);
    }
}
