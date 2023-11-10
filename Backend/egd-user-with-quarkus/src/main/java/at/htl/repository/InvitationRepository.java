package at.htl.repository;

import at.htl.model.Car;
import at.htl.model.Invitation;
import at.htl.model.UserCar;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;


@ApplicationScoped
public class InvitationRepository {
    @Inject
    EntityManager entityManager;


    @Inject
    UserCarRepository userCarRepository;

    @Transactional
    public Invitation addInv(Invitation invitation) {
        long id = userCarRepository.getUserCarIdByUserCar(invitation.getUserCar());
        invitation.getUserCar().setId(id);
        entityManager.persist(invitation);
        return invitation;
    }





    @Transactional
    public void removeInv(final long invId) {
        final Invitation invitation = findById(invId);
        entityManager.remove(invitation);
    }




    public Invitation findById(long id) {

        return entityManager.find(Invitation.class, id);
    }




    @Transactional
    public void updateInvation(Invitation invitation) {
        entityManager.merge(invitation);
    }

    public List<Invitation> getAllInv() {
        return entityManager.createQuery("SELECT i FROM Invitation i", Invitation.class)
                .getResultList();
    }


    public List<Invitation> getInvitatationsForUser(Long id) {
        var invs =  entityManager.createQuery("SELECT i FROM Invitation i WHERE i.userToInvite.id = :userId")
                .setParameter("userId", id).getResultList();

        return invs;
    }

    public List<Invitation> getInvitatationsSendForUser(Long id) {
        var invs =  entityManager.createQuery("SELECT i FROM Invitation i WHERE i.userCar.user.id = :userId")
                .setParameter("userId", id).getResultList();

        return invs;
    }
}
