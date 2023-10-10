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


    @Transactional
    public Invitation addInv(Invitation invitation) {
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


}
