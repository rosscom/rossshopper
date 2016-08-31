
package se.rosscom.shopper.client.login;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Authenticator {
    
    private JsonObject loggedIn;
    private String location;
    private Client client;
    private WebTarget accountTarget;
    
 
    @PostConstruct
    public void initClient() {
        this.client = ClientBuilder.newClient();
        this.accountTarget = this.client.target("http://localhost:8080/shopper/api/account");
 
    }

    
    private final Map<String, String> USERS = new HashMap<String, String>();
    {
        USERS.put("demo", "demo");
    }
    public boolean validate(String user, String password) {

        if ("demo".equals(user)) {
            String validUserPassword = USERS.get(user);
            return validUserPassword != null && validUserPassword.equals(password);
        } else {
            return true;
        }
        
    }
}