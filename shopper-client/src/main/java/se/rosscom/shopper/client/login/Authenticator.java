
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
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Authenticator {
    
    private JsonObject loggedIn;
    private String location;
    private Client client;
    private Client clientPing;
    private WebTarget familyTarget;
    private WebTarget pingTarget;
    

    @PostConstruct
    public void initClient() {
        this.client = ClientBuilder.newClient();
        this.familyTarget = this.client.target("http://localhost:8080/shopper/api/family");
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
                add("user", user).
                add("password", password).build();
        location = familyTarget.getUriBuilder().toString();
        System.out.println("location                      : ok "+location );

        JsonArray accountFamily = familyTarget.
                path(accountToFind.getString("user")).
                request(MediaType.APPLICATION_JSON).
                get(JsonArray.class);
        System.err.println("list family                   : " + accountFamily);
        
        if ("demo".equals(user)) {
            String validUserPassword = USERS.get(user);
            return validUserPassword != null && validUserPassword.equals(password);
        } else {
            return true;
        }
        
    }
}