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
import javax.persistence.TypedQuery;
import se.rosscom.shopper.business.account.entity.Account;
import se.rosscom.shopper.business.family.entity.AccountHomepk;
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
    public Family save(AccountHomepk family) {
        Account acc = this.em.find((Account.class), family.getAccount().getUser());
        acc.setChoosedHome(family.getHome().getName());
        return this.em.merge(new Family(family));
    }

    public Family findByHome(String home) {
       return this.em.find((Family.class), home); 
    }

    public Family findById(String id) {
       return this.em.find((Family.class), id); 
    }
    
    public List<Family> all() {
        return this.em.createNamedQuery(Family.findAll,Family.class).getResultList();
    }
    
    public Home findByName(String name) {
       return this.em.find((Home.class), name); 
    }
    
    public List<Family> findByUser(Account account) {
        TypedQuery<Family> typedQuery = this.em.createNamedQuery(Family.findByUser,Family.class);
        typedQuery.setParameter("user", account.getUser());
        List<Family> results = typedQuery.getResultList();
        	
        return results;
    }
    
    
    public void delete(String home) {
        Family reference = this.findByHome(home);
        this.em.remove(reference);
    }
}
