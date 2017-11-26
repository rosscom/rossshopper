package se.rosscom.shopper.business.family.boundary;

import se.rosscom.shopper.business.family.entity.Family;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class FamilyDao {

    @PersistenceContext
    private EntityManager em;

    public Family save(Family family) {
        return this.em.merge(family);
    }
}
