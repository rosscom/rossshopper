/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.family.boundary;

import se.rosscom.shopper.business.home.boundary.*;
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
    FamilyService familyService;

    @Inject
    HomeService homeService;

    @Inject
    AccountService accountService;

    @POST
    public Response save() {
//        Family saveFamily = familyService.save(home, account);
        //URI uri = info.getAbsolutePathBuilder().path("/"+saveFamily.getName().build();
        //return Response.created(uri).build();
        return Response.ok().build();
    }

    @GET
    @Path("{userId}")
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

    @GET
    public List<Family> all() {
        return familyService.all();
    }


    @DELETE
    @Path("{home}")
    public void delete(@PathParam("home") String name) {
        familyService.delete(name);
        
    }

}
