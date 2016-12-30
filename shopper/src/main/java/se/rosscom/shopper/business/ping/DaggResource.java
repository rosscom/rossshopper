/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.ping;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("dagg")
public class DaggResource {
    
    @GET
    public String get(){
        return "rosscom";
    }
    
}
