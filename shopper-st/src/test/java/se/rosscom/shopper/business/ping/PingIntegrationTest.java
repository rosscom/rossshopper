/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.ping;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import se.rosscom.shopper.business.ClientWrapper;
import se.rosscom.shopper.business.EntityHelper;
import se.rosscom.shopper.business.UserAndTokenHelper;
import org.junit.Test;

public class PingIntegrationTest {

    private WebTarget target = ClientWrapper.createClient("http://localhost:8080/shopper/api/ping");

    private final String userId = "ping";
    private final String token = UserAndTokenHelper.generateTokenThroughRequest(userId,"1234");
    
    @After
    public void tearDown() {
        EntityHelper.deleteAccountByUserId(userId, token);
    }

    @Test
    public void smoke() {
        Response response = target.request().header("Authorization", token).get();
        assertThat(response.getStatus(), is(200));
    }


    
}
