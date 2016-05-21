/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.family.boundary;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import static com.airhacks.rulz.jaxrsclient.JAXRSClientProvider.buildWithURI;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

/**
 *
 * @author ulfrossang
 */
public class FamilyResourceIT {
        
    @Rule
    public JAXRSClientProvider provider = buildWithURI("http://localhost:8080/shopper/api/family");
    public JAXRSClientProvider providerHome = buildWithURI("http://localhost:8080/shopper/api/home");
    public JAXRSClientProvider providerAccount = buildWithURI("http://localhost:8080/shopper/api/account");

       
    @Test
    public void crud() {
        
        // Create a home
        JsonObjectBuilder homeBuilder =  Json.createObjectBuilder();
        JsonObject homeToCreate = homeBuilder.
                add("name", "dagg").
                add("adress", "daggstigen 20").build();
        System.out.println("Create a home: dagg daggstigen 20");
        
        Response postResponseHome = this.providerHome.target().request().post(Entity.json(homeToCreate));
        assertThat(postResponseHome.getStatus(),is(201));
        String locationHome = postResponseHome.getHeaderString("Location");
        System.out.println("Check that we have a home dagg: " +locationHome);
        
        // Find home with name
        System.out.println("Find home dagg");
        JsonObject daggHome = this.provider.client().
               target(locationHome).
               request(MediaType.APPLICATION_JSON).
               get(JsonObject.class);
        assertTrue(daggHome.getString("name").contains("dagg"));        
        System.out.println("daggHome: " + daggHome.getString("name") + " " + daggHome.getString("adress"));

        // Create an account
        JsonObjectBuilder accountBuilder =  Json.createObjectBuilder();
        JsonObject accountToCreate = accountBuilder.
                add("user", "admin").
                add("password", "password").build();
        System.out.println("Create an account: admin pasword");

        Response postResponseAccount = this.providerAccount.target().request().post(Entity.json(accountToCreate));
        assertThat(postResponseAccount.getStatus(),is(201));
        String locationAccount = postResponseAccount.getHeaderString("Location");
        System.out.println("Check that we have an account admin: " +locationAccount);

        System.out.println("Find admin account");
        JsonObject adminAccount = this.provider.client().
               target(locationAccount).
               request(MediaType.APPLICATION_JSON).
               get(JsonObject.class);
        assertTrue(adminAccount.getString("user").contains("admin"));   
        System.out.println("adminAccount: " + adminAccount.getString("user") + " " + adminAccount.getString("password"));
        
        // Create a family
        JsonObjectBuilder familyBuilder =  Json.createObjectBuilder();
        JsonObject familyToCreate = familyBuilder.
                add("home", homeToCreate).
                add("account", accountToCreate).build();
         System.out.println("Create a family: " + homeToCreate.get("name") + " " + accountToCreate.getString("user"));
       
 
        System.out.println("Create family");
        Response postResponse = this.provider.target().request().post(Entity.json(familyToCreate));
        assertThat(postResponse.getStatus(),is(201));
        String location = postResponse.getHeaderString("Location");
        System.out.println("Check that we have a family: " +location);
        

        // listAll
        System.out.println("list all");
        Response response = provider.target().
                request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(),is(200));
        
        JsonArray allFamilys = response.readEntity(JsonArray.class);
        System.err.println("allFamilys " + allFamilys);
        assertFalse(allFamilys.isEmpty());

        
//       JsonObject familyOne = this.provider.target().
//               path("dagg").
//               request(MediaType.APPLICATION_JSON).
//               get(JsonObject.class);
//        assertTrue(familyOne.getString("name").contains("dagg"));
        
        // Find with name
        // Todo check response better
//        System.out.println("Find family by id");
//        JsonObject family = this.provider.client().
//               target(location).
//               request(MediaType.APPLICATION_JSON).
//               get(JsonObject.class);
//        assertFalse(family.isEmpty());       
//        System.out.println("family: " + family);
        
        // Update
//        System.out.println("check update");
//        JsonObjectBuilder updateBuilder =  Json.createObjectBuilder();
//        JsonObject updated = updateBuilder.
//                add("adress", "huddinge").build();
//        
//        this.provider.client().
//                target(location).
//                request(MediaType.APPLICATION_JSON).
//                put(Entity.json(updated));
//
//        // Find again
//        // Find
//        JsonObject updateHome = this.provider.client().
//                target(location).
//                request(MediaType.APPLICATION_JSON).
//                get(JsonObject.class);
//        assertTrue(updateHome.getString("adress").contains("huddinge"));  
//
//        // listAll again
//        response = provider.target().
//                request(MediaType.APPLICATION_JSON).get();
//        allHomes = response.readEntity(JsonArray.class);
//        System.err.println("allHomes " + allHomes);
//
//        // Create a family
//        System.out.println("check create Family");
//        // Create a home
//        // Create a home
//        JsonObjectBuilder accountBuilder =  Json.createObjectBuilder();
//        JsonObject accountToCreate = accountBuilder.
//                add("user", "admin").
//                add("password", "password").build();
//        
//        postResponse = this.provider.target().request().post(Entity.json(homeToCreate, accountToCreate));        
//        System.out.println("Create a home: dagg daggstigen 20");
//        
//        // Create 
//        Response postResponse = this.provider.target().request().post(Entity.json(homeToCreate));
//        assertThat(postResponse.getStatus(),is(201));
//        String location = postResponse.getHeaderString("Location");
//        System.out.println("Check that we have a home dagg: " +location);
//        
//        // Create 
//        Response postResponse = this.provider.target().request().post(Entity.json(homeToCreate));
//        assertThat(postResponse.getStatus(),is(201));
//        String location = postResponse.getHeaderString("Location");
//        System.out.println("Check that we have a home dagg: " +location);
        
        
        // delete
//        System.out.println("check delete");
//        Response deleteResponse = this.provider.target().
//               path("dagg").
//               request(MediaType.APPLICATION_JSON).delete();
//        assertThat(deleteResponse.getStatus(), is(204));
//        
//        // listAll again after delete
//        response = provider.target().
//                request(MediaType.APPLICATION_JSON).get();
//        allHomes = response.readEntity(JsonArray.class);
//        System.err.println("allHomes " + allHomes);
    }

}
