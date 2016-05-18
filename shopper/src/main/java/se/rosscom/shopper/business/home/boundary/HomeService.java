/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.home.boundary;

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
public class HomeService {
    
    @PersistenceContext
    EntityManager em;
    
    public Home save(Home home) {
        return this.em.merge(home);
    }

    public Home findByName(String name) {
       return this.em.find((Home.class), name); 
    }

    public List<Home> all() {
        return this.em.createNamedQuery(Home.findAll,Home.class).getResultList();
    }
    
    // family
    public Family save(Home home, Account account) {
        Family family = new Family();
        family.setAccount(account);
        family.setHome(home);
        return this.em.merge(family);
    }
    
    
    public void delete(String name) {
        Home reference = this.em.getReference(Home.class, name);
        this.em.remove(reference);
    }
    
  
    public void addUser(String userId, Long homeId) {
        
    }
   
}
