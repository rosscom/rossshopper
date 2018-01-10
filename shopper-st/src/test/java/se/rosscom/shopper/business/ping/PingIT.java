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

import se.rosscom.shopper.business.UserAndTokenHelper;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author ulfrossang
 */
public class PingIT {

    private WebTarget webTarget;
    
    @Before
    public void initClient() {
        Client client = ClientBuilder.newClient();
        this.webTarget = client.target("http://localhost:8080/shopper/api/ping");
    }
    
//    @Test
    public void smoke() {
        Response response = this.webTarget.request().header("Authorization", UserAndTokenHelper.generateTokenThroughRequest("user","1234")).get();
        assertThat(response.getStatus(), is(200));
        String result = response.readEntity(String.class);
        System.err.println("Result: " + result);
    }
    
}
