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

    public Family findByHome(String home) {
       return this.em.find((Family.class), home); 
    }

    public Family findById(long id) {
       return this.em.find((Family.class), id); 
    }
    
    public List<Family> all() {
        return this.em.createNamedQuery(Family.findAll,Family.class).getResultList();
    }
    
    public Home findByName(String name) {
       return this.em.find((Home.class), name); 
    }
    
    public Account findByUser(String user) {
       return this.em.find((Account.class), user); 
    }
    
    public void delete(String home) {
        Family reference = this.findByHome(home);
        this.em.remove(reference);
    }
}
