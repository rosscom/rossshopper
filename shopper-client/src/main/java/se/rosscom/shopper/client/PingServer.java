/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author ulfrossang
 */
public class PingServer {
    
    private Client clientPing;
    private WebTarget pingTarget;

         
    public boolean connectToServer() {
        this.clientPing = ClientBuilder.newClient();
        this.pingTarget = this.clientPing.target("http://localhost:8080/shopper/api/dagg");
        // Check the server
        
        try {
            Response response = this.pingTarget.request().get();
            if (response != null && response.getStatus()==200){
                String result = response.readEntity(String.class);
                System.err.println("Result: " + result);
                return true;
            }
        } catch (RuntimeException e) {
                e.printStackTrace();
             return false;
        }
        return false;
    }
    
}
