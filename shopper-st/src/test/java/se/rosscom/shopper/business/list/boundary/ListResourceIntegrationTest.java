/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.list.boundary;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import static com.airhacks.rulz.jaxrsclient.JAXRSClientProvider.buildWithURI;
import java.util.Base64;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Rule;

/**
 *
 * @author ulfrossang
 */
public class ListResourceIntegrationTest {
    
        
    @Rule
    public JAXRSClientProvider provider = buildWithURI("http://localhost:8080/shopper/api/listdetail");
    public JAXRSClientProvider providerHome = buildWithURI("http://localhost:8080/shopper/api/home");
    public JAXRSClientProvider providerAccount = buildWithURI("http://localhost:8080/shopper/api/account");
    public JAXRSClientProvider providerFamily = buildWithURI("http://localhost:8080/shopper/api/family");
    public JAXRSClientProvider providerLogin = buildWithURI("http://localhost:8080/shopper/api/auth/login");

       
//    @Test
    public void crud() {
        
        
        // Create a home
        JsonObjectBuilder homeBuilder =  Json.createObjectBuilder();
        JsonObject homeToCreate = homeBuilder.
                add("name", "dagg").
                add("adress", "daggstigen 20").build();
        
        Response postResponseHome = this.providerHome.target().request().post(Entity.json(homeToCreate));
        assertThat(postResponseHome.getStatus(),is(201));
        String locationHome = postResponseHome.getHeaderString("Location");
        System.out.println("Create a home                 : ok "+ homeToCreate.toString());
        
        // Find home with name
        JsonObject daggHome = this.providerHome.client().
               target(locationHome).
               request(MediaType.APPLICATION_JSON).
               get(JsonObject.class);
        assertTrue(daggHome.getString("name").contains("dagg"));        
        System.out.println("Find dagg home                : ok " + daggHome.toString());

        // Create an account
        JsonObjectBuilder accountBuilder =  Json.createObjectBuilder();
        JsonObject accountToCreate = accountBuilder.
                add("userId", "shoppertest").
                add("password", "timon").build();

        Response postResponseAccount = this.providerAccount.target().request().post(Entity.json(accountToCreate));
        assertThat(postResponseAccount.getStatus(),is(200));
        System.out.println("Create an account             : ok "+ accountToCreate.toString());

        
        
        String basicAuthString =  "Basic " + Base64.getEncoder().encodeToString(("shoppertest:timon").getBytes());
        String token = this.providerLogin.target().
                request().
                header("Authorization", basicAuthString).
                get(String.class);
        assertThat(token, startsWith("Auth-shopper"));
        
        System.out.println("Create an account               : ok "+ accountToCreate.toString());
        System.out.println("token                           : " +token);
        String location = providerAccount.target().getUri()+"/"+accountToCreate.getString("userId");

        JsonObject adminAccount = this.providerAccount.client().
               target(location).
               request(MediaType.APPLICATION_JSON).
               header("Authorization", token).                
               get(JsonObject.class);
        assertTrue(adminAccount.getString("userId").contains("shoppertest"));   
        System.out.println("Find admin account            : ok " + adminAccount.toString());
        
        // Create a family
        JsonObjectBuilder familyBuilder =  Json.createObjectBuilder();
        JsonObject familyToCreate = familyBuilder.
                add("home", daggHome).
                add("account", adminAccount).build();
       
 
        Response postResponse = this.providerFamily.target().request().post(Entity.json(familyToCreate));
        assertThat(postResponse.getStatus(),is(201));
        location = postResponse.getHeaderString("Location");
        System.out.println("Create an family              : ok "+ familyToCreate.toString());
        System.out.println("location                      : ok "+location );

        
        System.out.println(familyToCreate.getJsonObject("home"));
        System.out.println(familyToCreate.getJsonObject("account"));

        

        // listAll
        Response response = providerFamily.target().
                request(MediaType.APPLICATION_JSON).
                header("Authorization", token).
                get();
        assertThat(response.getStatus(),is(200));
        
        JsonArray allFamilys = response.readEntity(JsonArray.class);
        System.out.println("list allFamilys               : " + allFamilys);
        assertFalse(allFamilys.isEmpty());

        // Find with family TODO
//        JsonObject familyDaggShopper = this.provider.client().
//               target(location).
//               request(MediaType.APPLICATION_JSON).
//               get(JsonObject.class);
//        assertTrue(familyDaggShopper.getString("home").contains("dagg"));        
//        System.out.println("Find family with dagg shopper    : ok " + familyDaggShopper.toString());
//
//        // Find admin account
//        accountBuilder =  Json.createObjectBuilder();
//        JsonObject accountToFind = accountBuilder.
//                add("userId", "admin").
//                add("password", "password").build();
//        location = this.providerFamily.target().getUriBuilder().toString();
//        System.out.println("location                      : ok "+location );

        /* TODO
        JsonArray accountFamily = this.providerFamily.client().
                target(location).
                path(accountToFind.getString("userId")).
                request(MediaType.APPLICATION_JSON).
                get(JsonArray.class);
        assertTrue(accountFamily.size()>0);
        System.out.println("                              :"+accountToFind.getString("userId"));
        System.err.println("list family                   : " + accountFamily);
        

        // listAll
        response = provider.target().
                request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(),is(200));
        
        JsonArray allListDetail = response.readEntity(JsonArray.class);
        System.err.println("allListDetail                 : ok " + allListDetail);

        */
//        assertFalse(allListDetail.isEmpty());
        

    }

    
}
