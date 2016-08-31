/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.ping;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author ulfrossang
 */
public class DaggIT {
    
    private Client client;
    private WebTarget tut;
    
    @Before
    public void initClient() {
        this.client = ClientBuilder.newClient();
        this.tut = this.client.target("http://localhost:8080/shopper/api/dagg");
       
        
    }
    
    @Test
    public void smoke() {
        Response response = this.tut.request().get();
        assertThat(response.getStatus(), is(200));
        String result = response.readEntity(String.class);
        System.err.println("Result: " + result);
        
    }
    
}
