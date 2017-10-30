/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.ping;

import se.rosscom.shopper.business.authentication.boundary.Secured;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;

@Path("ping")
public class PingResource {

    @Inject
    private PingService pingService;

    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject get(){
        return pingService.ping();
    }
    
}
