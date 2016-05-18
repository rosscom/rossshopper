/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.family.boundary;

import se.rosscom.shopper.business.home.boundary.*;
import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import se.rosscom.shopper.business.family.entity.Family;

/**
 *
 * @author ulfrossang
 */
@Stateless
@Path("family")
public class FamilyResource {
    
    @Inject
    FamilyService familyService;

    @Inject
    HomeService homeService;

    @POST
    public Response save(Family family, @Context UriInfo info) {
        Family saveFamily = familyService.save(family);  
        Long familyId = saveFamily.getFamilyId();
        URI uri = info.getAbsolutePathBuilder().path("/"+familyId).build();
        return Response.created(uri).build();
    }
    @GET
    @Path("{id}")
    public Family find(@PathParam("id") long id) {
        return familyService.findById(id);
    }

    @GET
    @Path("{home}")
    public Family find(@PathParam("home") String home) {
        return familyService.findByHome(home);
    }

    @GET
    public List<Family> all() {
        return familyService.all();
    }


    @DELETE
    @Path("{home}")
    public void delete(@PathParam("home") String home) {
        familyService.delete(home);
        
    }

}
