/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.list.boundary;

import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import se.rosscom.shopper.business.family.boundary.FamilyService;
import se.rosscom.shopper.business.family.entity.Family;
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
 
    @Inject
    FamilyService familyService;
        
    @POST
    public Response save(ListDetail listdetail, @Context UriInfo info) {
        ListDetail saveDetail = listService.save(listdetail);    
        Long detailId = saveDetail.getId();
        URI uri = info.getAbsolutePathBuilder().path("/"+detailId).build();
        return Response.created(uri).build();
    }
    
    @GET
    public List<ListDetail> all() {
        return listService.all();
    }

    
}
