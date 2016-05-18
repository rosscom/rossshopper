/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.list.boundary;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import se.rosscom.shopper.business.list.entity.ListDetail;

/**
 *
 * @author ulfrossang
 */
@Stateless
@Path("listdetail")
public class ListResource {

    @Inject
    ListService listService;
    
        
    @POST
    public void save(ListDetail listdetail) {
        listService.save(listdetail);    
    }
    
    @GET
    public List<ListDetail> all() {
        return listService.all();
    }

    
}
