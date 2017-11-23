package se.rosscom.shopper.business.list.boundary;

import se.rosscom.shopper.business.family.entity.Family;
import se.rosscom.shopper.business.list.entity.ListDetail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ListDao {

    @PersistenceContext
    EntityManager em;

    public ListDetail findById(Integer id) {
        return this.em.find((ListDetail.class), id);
    }

    public Family findByFamilyId(Long familyId) {
        return this.em.find((Family.class), familyId);
    }

    public List<ListDetail> findByFamily(Long familyId) {
        return this.em.createQuery("SELECT l FROM list_detail l WHERE l.family.familyId = :familyId", ListDetail.class)
                .setParameter("familyId", familyId)
                .getResultList();
    }

    public void persist(ListDetail listDetail) {
        this.em.persist(listDetail);
    }

    public List<ListDetail> findAll() {
        return this.em.createQuery("SELECT t from list_detail t", ListDetail.class).getResultList();
    }

    public void remove(Integer id) {
        ListDetail reference = this.em.getReference(ListDetail.class, id);
        this.em.remove(reference);
    }
}
