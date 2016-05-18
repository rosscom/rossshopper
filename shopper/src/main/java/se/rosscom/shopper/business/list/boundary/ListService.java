/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.list.boundary;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import se.rosscom.shopper.business.list.entity.ListDetail;

/**
 *
 * @author ulfrossang
 */
@Stateless
public class ListService {
    
    @PersistenceContext
    EntityManager em;
    
    
    
    public void save(ListDetail list) {
        this.em.merge(list);
    }

    public void addItem(ListDetail item) {
        this.em.merge(item);
        
    }
    public void removeItem(String item) {
        ListDetail reference = this.em.getReference(ListDetail.class, item);
        this.em.remove(reference);
    }
        
    public List<ListDetail> all() {
        return this.em.createNamedQuery(ListDetail.findAll,ListDetail.class).getResultList();
    }

}
