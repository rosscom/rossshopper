/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.account.boundary;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import se.rosscom.shopper.business.account.entity.Account;

/**
 *
 * @author ulfrossang
 */
@Stateless
public class AccountService {
  
    @PersistenceContext
    private EntityManager em;


    public Account save(Account account){
        Account savedAcccount = this.em.merge(account);
        return savedAcccount;
    }
    
    public Account findByUser(String userId) {
       Account account = this.em.find((Account.class), userId); 
       if (account == null) {
           return null;
       }
       return account;
    }


    public List<Account> findByLoggedIn(String loggedIn) {
        return this.em.createNamedQuery(Account.findByLoggedIn,Account.class).getResultList();
    }

    public List<Account> all() {
        return this.em.createNamedQuery(Account.findAll,Account.class).getResultList();
    }

    public void delete(String user) {
        this.em.createNativeQuery("DELETE FROM token WHERE account = ?1")
                .setParameter(1, user).executeUpdate();

        this.em.createNativeQuery("DELETE FROM account WHERE userid = ?1")
                .setParameter(1, user).executeUpdate();
    }

    
}
