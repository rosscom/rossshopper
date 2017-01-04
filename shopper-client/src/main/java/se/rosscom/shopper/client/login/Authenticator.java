
package se.rosscom.shopper.client.login;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThat;

public class Authenticator {
    
    private JsonObject loggedIn;
    private String location;
    private Client client;
    private Client clientPing;
    private WebTarget familyTarget;
    private WebTarget accountTarget;
    private WebTarget pingTarget;
    

    @PostConstruct
    public void initClient() {
        this.client = ClientBuilder.newClient();
        this.familyTarget = this.client.target("http://localhost:8080/shopper/api/family");
        this.accountTarget = this.client.target("http://localhost:8080/shopper/api/account");
        this.clientPing = ClientBuilder.newClient();
        this.pingTarget = this.clientPing.target("http://localhost:8080/shopper/api/dagg");

 
    }

    
    private final Map<String, String> USERS = new HashMap<String, String>();
    {
        USERS.put("demo", "demo");
    }
    public boolean validate(String user, String password) {

        Response response = this.pingTarget.request().get();
        if (response != null && response.getStatus()==200){
            String result = response.readEntity(String.class);
            System.err.println("Result: " + result);
        }

        // Find admin account
        JsonObjectBuilder accountBuilder =  Json.createObjectBuilder();
        JsonObject accountToFind = accountBuilder.
                add("userId", user).
                add("password", password).build();
        location = familyTarget.getUriBuilder().toString();
        System.out.println("location                      : ok "+location );

        response = familyTarget.
                path(accountToFind.getString("userId")).
                request(MediaType.APPLICATION_JSON).
                get();
        
        if (response != null && response.getStatus() == 200){
            String result = response.readEntity(String.class);
            System.err.println("Result: " + result); 
            JsonArray accountFamily = response.readEntity(JsonArray.class);
            System.err.println("list family                   : " + accountFamily);
        } else {
            // create a new account
            // Create an account json
            Response postResponse = accountTarget.
                    request().
                    post(Entity.json(accountToFind));
            if (postResponse != null && postResponse.getStatus() == 201){
                String result = response.readEntity(String.class);
                System.err.println("Result: " + result); 
                JsonObject account = postResponse.readEntity(JsonObject.class);
                System.err.println("new account                  : " + account);

                response = accountTarget.
                        path(accountToFind.getString("userId")).
                        request(MediaType.APPLICATION_JSON).
                        get();
                       
                if (postResponse != null && postResponse.getStatus() == 201){
                    result = response.readEntity(String.class);
                    System.err.println("Result: " + result); 
                    account = postResponse.readEntity(JsonObject.class);
                    System.err.println("new account                  : " + account);
                }
            }
        }
        
        if ("demo".equals(user)) {
            String validUserPassword = USERS.get(user);
            return validUserPassword != null && validUserPassword.equals(password);
        } else {
            return true;
        }
        
    }
}