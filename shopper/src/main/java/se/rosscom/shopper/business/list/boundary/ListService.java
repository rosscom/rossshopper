/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.list.boundary;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import se.rosscom.shopper.business.family.entity.Family;
import se.rosscom.shopper.business.list.entity.ListDetail;

/**
 *
 * @author ulfrossang
 */
@Stateless
public class ListService {
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    ListItemEndpoint listItemEndpoint;
    
    
    
    public ListDetail save(ListDetail list) {
        ListDetail listDetail = this.em.merge(list);
        String message = "item: "+ listDetail.getId() + " " + listDetail.getItem();
        System.out.println(message);
        listItemEndpoint.sendMessage(message);
        return listDetail;
    }
    
    public List<ListDetail> findByFamily(String familyId) {
        
        TypedQuery<ListDetail> typedQuery = this.em.createNamedQuery(ListDetail.findByFamily,ListDetail.class);
        typedQuery.setParameter("familyId", familyId);
        List<ListDetail> results = typedQuery.getResultList();
        return results;
//       return this.em.find((ListDetail.class), family); 
    }

    public ListDetail findById(long id) {
       return this.em.find((ListDetail.class), id); 
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
