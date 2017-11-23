/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.family.boundary;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import se.rosscom.shopper.business.account.boundary.AccountService;
import se.rosscom.shopper.business.account.entity.Account;
import se.rosscom.shopper.business.family.entity.Family;
import se.rosscom.shopper.business.family.entity.FamilyPojo;
import se.rosscom.shopper.business.home.boundary.HomeService;
import se.rosscom.shopper.business.home.entity.Home;

/**
 *
 * @author ulfrossang
 */
@Stateless
public class FamilyService {
    
    @PersistenceContext
    EntityManager em;

    @Inject
    private FamilyDao dao;

    @Inject
    private AccountService accountService; //TODO should be changed to AccountDao

    @Inject
    private HomeService homeService;

    // family
    public Family save(FamilyPojo pojo) {
        Account user = accountService.findByUser(pojo.userId);
        Home home = homeService.findByName(pojo.homeName);
        Family family = new Family();
        family.setAccount(user);
        family.setHome(home);

        return dao.save(family);
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
        List<Family> resultList = em.createQuery("SELECT f FROM Family f WHERE f.account.userId = :userId"
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
