package at.htl.repository;

import at.htl.model.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class CostsRepository {

    @Inject
    EntityManager entityManager;

    @Inject
    AccidentRepository accidentRepository;

    public List<Costs> findAllCosts() {
        List<Costs> allCosts = entityManager.createQuery("SELECT c FROM Costs c", Costs.class)
                .getResultList();

        return allCosts;
    }

    public List<Costs> findAllCostsByCarId(long carId) {
        return entityManager.createQuery("SELECT c FROM Costs c WHERE c.userCar.car.id = :carId", Costs.class)
                .setParameter("carId", carId)
                .getResultList();
    }

    public List<Costs> findCostsByUserId(long userId) {

        List<Costs> costs= entityManager.createQuery("SELECT c FROM Costs c WHERE c.userCar.id = :userId", Costs.class)
                .setParameter("userId", userId)
                .getResultList();

       return  costs;
    }

    @Transactional
    public Costs addCosts(Costs costs) {
        entityManager.persist(costs);
        return costs;
    }

    @Transactional
    public void removeCosts(Long costsId) {
        Costs costs = entityManager.find(Costs.class, costsId);

        Accident accident = accidentRepository.findByCostsId(costsId);
        accidentRepository.removeAccident(accident.getId());

        entityManager.remove(costs);
    }

    public List<Costs> getAllCostsByDateRange(LocalDate fromDate, LocalDate toDate, Long carId) {
        String queryString = "SELECT d FROM Costs d WHERE d.userCar.car.id = :carId AND d.date BETWEEN :fromDate AND :toDate";
        TypedQuery<Costs> query = entityManager.createQuery(queryString, Costs.class);
        query.setParameter("carId", carId);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        return query.getResultList();
    }
}
