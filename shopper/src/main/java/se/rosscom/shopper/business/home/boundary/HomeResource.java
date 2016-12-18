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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import se.rosscom.shopper.business.account.entity.Account;
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

    @POST
    public Response save(Home home, @Context UriInfo info) {
        Home saveHome = homeService.save(home);  
        String name = saveHome.getName();
        URI uri = info.getAbsolutePathBuilder().path("/"+name).build();
        return Response.created(uri).build();
    }

    @GET
    @Path("{name}")
    public Home find(@PathParam("name") String name) {
        return homeService.findByName(name);
    }

    @GET
    public List<Home> all() {
        return homeService.all();
    }

    @PUT
    @Path("{name}")
    public Home update(@PathParam("name") String name, Home home) {
        home.setName(name);
        return homeService.save(home);
    }
    
    @POST
    public Response save(Home home, Account account, @Context UriInfo info) {
        Family family = homeService.save(home, account);  
        URI uri = info.getAbsolutePathBuilder().path("/"+family.getId().getHome().getName()).build();
        return Response.created(uri).build();
    }
    

    @DELETE
    @Path("{name}")
    public void delete(@PathParam("name") String name) {
        homeService.delete(name);
        
    }

}
