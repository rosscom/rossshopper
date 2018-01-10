/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.family.boundary;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import se.rosscom.shopper.business.account.entity.Account;
import se.rosscom.shopper.business.family.entity.Family;
import se.rosscom.shopper.business.home.entity.Home;

/**
 *
 * @author ulfrossang
 */
@Stateless
public class FamilyService {
    
    @PersistenceContext
    EntityManager em;
    
    // family
    public Family save(Family family) {
        return this.em.merge(family);
    }

    public Family findByHome(Long homeId) {
       return this.em.find((Family.class), homeId);
    }
    
    public List<Family> all() {
        return this.em.createNamedQuery(Family.findAll,Family.class).getResultList();
    }
    
    public Family findByFamilyId(Long familyId) {
       return this.em.find((Family.class), familyId);
    }
    
    public List<Family> findByUser(Account account) {
        List<Family> resultList = em.createQuery("SELECT f FROM Family f WHERE f.id.userId = :userId"
                , Family.class)
                .setParameter("userId", account.getUserId())
                .getResultList();
        return resultList.isEmpty() ? null : resultList;
    }
    
    
    public void delete(Long familyId) {
        Family reference = this.findByHome(familyId);
        this.em.remove(reference);
    }
}
