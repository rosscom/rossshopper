/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.family.boundary;


import java.net.URI;
import se.rosscom.shopper.business.authentication.boundary.Secured;
import se.rosscom.shopper.business.family.entity.FamilyPojo;
import se.rosscom.shopper.business.home.boundary.*;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import se.rosscom.shopper.business.account.boundary.AccountService;
import se.rosscom.shopper.business.account.entity.Account;
import se.rosscom.shopper.business.family.entity.Family;
import se.rosscom.shopper.business.home.entity.Home;

/**
 *
 * @author ulfrossang
 */
@Stateless
@Path("family")
public class FamilyResource {
    
    @Inject
    private FamilyService familyService;

    @Inject
    private AccountService accountService;

    @Secured
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(FamilyPojo pojo, @Context UriInfo info) {
        Family saveFamily = familyService.save(pojo);
        URI uri = info.getAbsolutePathBuilder().path("/"+saveFamily.getFamilyId()).build();
        return Response.created(uri).build();
    }

    @Secured
    @GET
    @Path("{family}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("family") Long familyId) {
        Family family = familyService.findByFamilyId(familyId);
        if (family == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    header("reason", "family: " + familyId + " dont exist").
                    build();
        } else {
            return Response.ok(family).build();
        }
    }

    @Secured
    @GET
    @Path("/userId/{userId}")
    public Response findByUser(@PathParam("userId") String userId) {
        Account account = accountService.findByUser(userId);
        if (account == null) {
            return Response.status(Response.Status.NOT_FOUND).
                    header("reason", "user: " + userId + " dont exist").
                    build();
        } else {
            List<Family> famList = familyService.findByUser(account);
            if (famList == null || (famList.isEmpty())) {
                return Response.status(Response.Status.NOT_FOUND).
                    header("reason", "user: " + userId + " dont exist").
                    build();
            } else {
                return Response.ok(famList).build();
            }
        }
    }

    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Family> all() {
        return familyService.all();
    }


    @Secured
    @DELETE
    @Path("{familyId}")
    public void delete(@PathParam("familyId") Long familyId) {
        familyService.delete(familyId);
    }

}
