
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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Authenticator {
    
    private JsonObject loggedIn;
    private String location;
    private Client client;
    private WebTarget familyTarget;
    private WebTarget accountTarget;
    

    @PostConstruct
    public void initClient() {
        this.client = ClientBuilder.newClient();
        this.familyTarget = this.client.target("http://localhost:8080/shopper/api/family");
        this.accountTarget = this.client.target("http://localhost:8080/shopper/api/account");
 
    }

    
    private final Map<String, String> USERS = new HashMap<String, String>();
    {
        USERS.put("demo", "demo");
    }
    public boolean validate(String user, String password) {

        // Find admin account
        JsonObjectBuilder accountBuilder =  Json.createObjectBuilder();
        JsonObject accountToFind = accountBuilder.
                add("userId", user).
                add("password", password).build();
        location = familyTarget.getUriBuilder().toString();
        System.out.println("location                      : ok "+location );

        // check login
        if (accountToFind.getString("password").contains(password)) {
            // Do we have a family configured?
            Response response = familyTarget.
                    path(accountToFind.getString("userId")).
                    request(MediaType.APPLICATION_JSON).
                    get();

            if (response != null && response.getStatus() == 200){
                // Yes
                JsonArray accountFamily = response.readEntity(JsonArray.class);
                System.err.println("list family                   : " + accountFamily);
            }
            return true;
            

        } else {
            return false;

        }
            
            // No 
            // create a new account
            // Create an account json
//            Response postResponse = accountTarget.
//                    request().
//                    post(Entity.json(accountToFind));
//            if (postResponse != null && postResponse.getStatus() == 201){
//                String result = response.readEntity(String.class);
//                System.err.println("Result: " + result); 
//                JsonObject account = postResponse.readEntity(JsonObject.class);
//                System.err.println("new account                  : " + account);
//
//                response = accountTarget.
//                        path(accountToFind.getString("userId")).
//                        request(MediaType.APPLICATION_JSON).
//                        get();
//                       
//                if (postResponse != null && postResponse.getStatus() == 201){
//                    result = response.readEntity(String.class);
//                    System.err.println("Result: " + result); 
//                    account = postResponse.readEntity(JsonObject.class);
//                    System.err.println("new account                  : " + account);
//                }
//            }        
//        if ("demo".equals(user)) {
//            String validUserPassword = USERS.get(user);
//            return validUserPassword != null && validUserPassword.equals(password);
//        } else {
//            return true;
//        }
        
    }
}