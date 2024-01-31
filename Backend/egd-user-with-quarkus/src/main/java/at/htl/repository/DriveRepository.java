package at.htl.repository;

import at.htl.Classes.DateDto;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
            entityManager.remove(drive);

    }


    public List<Drive> getAllDrivesByUserId(long userCarId) {
        String queryString = "SELECT d FROM Drive d WHERE d.userCar.id = :userCarId";
        TypedQuery<Drive> query = entityManager.createQuery(queryString, Drive.class);
        query.setParameter("userCarId", userCarId);

        return query.getResultList();
    }

    public List<Drive> getAllDrivesByUserIdBetween(DateDto dateDto) {

//        List<Drive> drives = getAllDrivesByUserId(dateDto.getCarId());
//
//        LocalDate fromDate = dateDto.getFromDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate toDate = dateDto.getToDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//        List<Drive> filteredDrives = drives.stream()
//                .filter(drive -> {
//                    LocalDate driveDate = new Date(drive.getDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                    return (driveDate.isEqual(fromDate) || driveDate.isAfter(fromDate))
//                            && (driveDate.isEqual(toDate) || driveDate.isBefore(toDate));
//                })
//                .collect(Collectors.toList());

        return null;
    }


   // getAllDrivesByCar;    (Gesamtübersicht für alle die dieses Auto haben)
    public List<Drive> getAllDrivesByCar(long carId){
        String queryString = "SELECT d FROM Drive d WHERE d.userCar.car.id = :carId";
        TypedQuery<Drive> query = entityManager.createQuery(queryString, Drive.class);
        query.setParameter("carId", carId);

        return query.getResultList();
    }

    public List<Drive> getAllDrives() {
        String queryString = "SELECT d FROM Drive d";
        TypedQuery<Drive> query = entityManager.createQuery(queryString, Drive.class);

        return query.getResultList();
    }
    private Drive findByDriveId(long driveId) {
        return entityManager.find(Drive.class, driveId);
    }

    public List<Drive> getDrivesByDateRange(LocalDate fromDate, LocalDate toDate, Long carId) {
        String queryString = "SELECT d FROM Drive d WHERE d.userCar.car.id = :carId AND d.date BETWEEN :fromDate AND :toDate";
        TypedQuery<Drive> query = entityManager.createQuery(queryString, Drive.class);
        query.setParameter("carId", carId);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);

        return query.getResultList();
    }
}
