/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.account.boundary;

import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
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

/**
 *
 * @author ulfrossang
 */
@Stateless
@Path("account")
public class AccountResource {
    
    @Inject
    AccountService accountService;

    @POST
    public Response save(Account account, @Context UriInfo info) {
        Account savedAccount = accountService.save(account);   
        String user = savedAccount.getUser();
        URI uri = info.getAbsolutePathBuilder().path("/"+user).build();
        return Response.created(uri).build();
    }

    @GET
    @Path("{user}")
    public Account find(@PathParam("user") String user) {
        return accountService.findByUser(user);
    }

    @GET
    public List<Account> all() {
        return accountService.all();
    }

    @PUT
    @Path("{user}")
    public Account update(@PathParam("user") String user, Account account) {
        account.setUser(user);
        return accountService.save(account);
    }

    // Subresource
    @PUT
    @Path("{user}/login")
    public Account login(@PathParam("user") String user, JsonObject statusUpdate) {
        Boolean loggedin = statusUpdate.getBoolean("loggedIn");
        return accountService.login(user, loggedin);
    }
    
    @DELETE
    @Path("{user}")
    public void delete(@PathParam("user") String user) {
        accountService.delete(user);
    }
    

    
    
    
}
