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
    EntityManager em;

    public Account save(Account account){
        return this.em.merge(account);
    }
    
    public Account findByUser(String userId) {
       return this.em.find((Account.class), userId); 
    }

    public List<Account> all() {
        return this.em.createNamedQuery(Account.findAll,Account.class).getResultList();
    }

    public Account login(String user, boolean loggedin) {
        Account account = this.findByUser(user);
        account.setLoggedIn(loggedin);
        return account;
    }

    public boolean login(Account account, boolean loggedin) {
        Account checkAccount = this.findByUser(account.getUserId());
        if (account.getPassword().equals(checkAccount.getPassword())) {
            return true;
        } else {
            return false;
        }
    }
    
    public void delete(String user) {
        Account reference = this.em.getReference(Account.class, user);
        this.em.remove(reference);
    }

    
}
