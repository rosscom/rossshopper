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
    public JAXRSClientProvider providerFamily = buildWithURI("http://localhost:8080/shopper/api/family");

       
    @Test
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
        JsonObject daggHome = this.provider.client().
               target(locationHome).
               request(MediaType.APPLICATION_JSON).
               get(JsonObject.class);
        assertTrue(daggHome.getString("name").contains("dagg"));        
        System.out.println("Find dagg home                : ok " + daggHome.toString());

        // Create an account
        JsonObjectBuilder accountBuilder =  Json.createObjectBuilder();
        JsonObject accountToCreate = accountBuilder.
                add("userId", "admin").
                add("password", "password").build();

        Response postResponseAccount = this.providerAccount.target().request().post(Entity.json(accountToCreate));
        assertThat(postResponseAccount.getStatus(),is(201));
        String locationAccount = postResponseAccount.getHeaderString("Location");
        System.out.println("Create an account             : ok "+ accountToCreate.toString());

        JsonObject adminAccount = this.provider.client().
               target(locationAccount).
               request(MediaType.APPLICATION_JSON).
               get(JsonObject.class);
        assertTrue(adminAccount.getString("userId").contains("admin"));   
        System.out.println("Find admin account            : ok " + adminAccount.toString());
        
        // Create a family
        JsonObjectBuilder familyBuilder =  Json.createObjectBuilder();
        JsonObject familyToCreate = familyBuilder.
                add("home", daggHome).
                add("account", adminAccount).build();
       
 
        Response postResponse = this.provider.target().request().post(Entity.json(familyToCreate));
        assertThat(postResponse.getStatus(),is(201));
        String location = postResponse.getHeaderString("Location");
        System.out.println("Create an family              : ok "+ familyToCreate.toString());
        System.out.println("location                      : ok "+location );

        
        System.out.println(familyToCreate.getJsonObject("home"));
        System.out.println(familyToCreate.getJsonObject("account"));

        // listAll
        Response response = provider.target().
                request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(),is(200));
        
        JsonArray allFamilys = response.readEntity(JsonArray.class);
        System.err.println("list allFamilys               : " + allFamilys);
        assertFalse(allFamilys.isEmpty());

        // Find admin account
        accountBuilder =  Json.createObjectBuilder();
        JsonObject accountToFind = accountBuilder.
                add("userId", "admin").
                add("password", "password").build();
        location = this.providerFamily.target().getUriBuilder().toString();
        System.out.println("location                      : ok "+location );

        JsonArray accountFamily = this.providerFamily.client().
                target(location).
                path(accountToFind.getString("userId")).
                request(MediaType.APPLICATION_JSON).
                get(JsonArray.class);
        assertTrue(accountFamily.size()>0);
        System.out.println("                              :"+accountToFind.getString("userId"));
        System.err.println("list family                   : " + accountFamily);
       
        // Find other account
        accountBuilder =  Json.createObjectBuilder();
        JsonObject accountNotFind = accountBuilder.
                add("userId", "other").
                add("password", "password").build();
        location = this.providerFamily.target().getUriBuilder().toString();
        System.out.println("location                      : ok "+location );

        response = this.providerFamily.client().
                target(location).
                path(accountNotFind.getString("userId")).
                request(MediaType.APPLICATION_JSON).
                get();
        assertThat(response.getStatus(), is(404));
        assertFalse(response.getHeaderString("reason").isEmpty());
        System.out.println("findOther missed              : ok "+response.getStatus() + " " + response.getHeaderString("reason") );
        

        // delete todo
//        System.out.println("check delete");
//        Response deleteResponse = this.provider.target().
//               path("dagg").
//               request(MediaType.APPLICATION_JSON).delete();
//        assertThat(deleteResponse.getStatus(), is(204));
//        
//        // listAll again after delete
//        response = provider.target().
//                request(MediaType.APPLICATION_JSON).get();
//        allFamilys = response.readEntity(JsonArray.class);
//        System.err.println("allFamilys " + allFamilys);
    }

}
