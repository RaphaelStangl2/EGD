package at.htl.repository;

import at.htl.model.Car;
import at.htl.model.Invitation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;


@ApplicationScoped
public class InvitationRepository {
    @Inject
    EntityManager entityManager;


    @Transactional
    public Invitation addInv(Invitation invitation) {
        entityManager.persist(invitation);
        return invitation;
    }

    
}
