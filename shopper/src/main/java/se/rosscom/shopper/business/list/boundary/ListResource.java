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
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.sun.org.apache.regexp.internal.RE;
import se.rosscom.shopper.business.account.entity.Account;
import se.rosscom.shopper.business.authentication.boundary.Secured;
import se.rosscom.shopper.business.family.boundary.FamilyService;
import se.rosscom.shopper.business.list.entity.ListDetail;
import se.rosscom.shopper.business.list.entity.ListDetailPojo;

/**
 *
 * @author ulfrossang
 */
@Stateless
@Path("listdetail")
public class ListResource {

    @Inject
    private ListService listService;

    @Secured
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(ListDetailPojo pojo, @Context UriInfo info) {
        ListDetail saveDetail = listService.save(pojo);
        Long detailId = saveDetail.getId();
        URI uri = info.getAbsolutePathBuilder().path("/" + detailId).build();
        return Response.created(uri).build();
    }

    @Secured
    @GET
    @Path("{listDetail}")
    public Response find(@PathParam("listDetail") Integer listDetailId) {
        ListDetailPojo pojo = listService.findById(listDetailId);
        return Response.ok(pojo).build();
    }
    
    @Secured
    @GET
    @Path("/family/{family}")
    public Response findByFamily(@PathParam("family") Long family) {
        List<ListDetail> items = listService.findByFamily(family);
        if (items == null || (items.isEmpty())) {
            return Response.status(Response.Status.NOT_FOUND).
                header("reason", "family: " + family + " dont exist").
                build();
        } else {
            return Response.ok(items).build();
        }
    }

    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ListDetailPojo> all() {
        return listService.all();
    }

    @Secured
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer id) {
        listService.delete(id);
    }
}
