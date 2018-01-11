/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.list.boundary;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import se.rosscom.shopper.business.family.entity.Family;
import se.rosscom.shopper.business.list.entity.ListDetail;
import se.rosscom.shopper.business.list.entity.ListDetailPojo;

/**
 *
 * @author ulfrossang
 */
@Stateless
public class ListService {

    @Inject
    ListItemEndpoint listItemEndpoint;

    @Inject
    private ListDao dao;

    public ListDetail save(ListDetailPojo pojo) {
        Family fam = dao.findByFamilyId(pojo.familyId);

        ListDetail listDetail = new ListDetail();
        listDetail.setItem(pojo.item);
        listDetail.setFamily(fam);

        dao.persist(listDetail);

        String message = "list-detail: "+ listDetail.getId() + " item: " +  listDetail.getItem();
        listItemEndpoint.sendMessage(message);
        return listDetail;
    }

    public List<ListDetail> findByFamily(Long familyId) {
        return dao.findByFamily(familyId);
    }

    public ListDetailPojo findById(Integer id) {
        ListDetail detail = dao.findById(id);
        return new ListDetailPojo(detail.getItem(), detail.getFamily().getFamilyId());
    }

    public List<ListDetailPojo> all() {
        List<ListDetailPojo> detailPojos = new ArrayList<>();
        List<ListDetail> listDetails = dao.findAll();
        for (ListDetail listDetail : listDetails) {
            ListDetailPojo pojo = new ListDetailPojo();
            pojo.item = listDetail.getItem();
            pojo.familyId = listDetail.getFamily().getFamilyId();
            detailPojos.add(pojo);
        }

        return detailPojos;
    }

    public void delete(Integer id) {
        dao.remove(id);
    }

}
