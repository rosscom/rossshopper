/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.home.boundary;

import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import se.rosscom.shopper.business.account.entity.Account;
import se.rosscom.shopper.business.authentication.boundary.Secured;
import se.rosscom.shopper.business.family.entity.Family;
import se.rosscom.shopper.business.home.entity.Home;

/**
 *
 * @author ulfrossang
 */
@Stateless
@Path("home")
public class HomeResource {
    
    @Inject
    HomeService homeService;

    @Secured
    @POST
    public Response save(Home home, @Context UriInfo info) {
        Home saveHome = homeService.save(home);
        String name = saveHome.getName();
        URI uri = info.getAbsolutePathBuilder().path("/"+name).build();
        return Response.created(uri).build();
    }

    @Secured
    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Home find(@PathParam("name") String name) {
        return homeService.findByName(name);
    }

    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Home> all() {
        return homeService.all();
    }

    @Secured
    @PUT
    @Path("{name}")
    public Home update(@PathParam("name") String name, Home home) {
        home.setName(name);
        return homeService.save(home);
    }

    @Secured
    @DELETE
    @Path("{name}")
    public void delete(@PathParam("name") String name) {
        homeService.delete(name);
        
    }

}
