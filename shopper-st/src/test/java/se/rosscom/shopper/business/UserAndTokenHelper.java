package se.rosscom.shopper.business;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import static com.airhacks.rulz.jaxrsclient.JAXRSClientProvider.buildWithURI;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import java.util.Base64;
import javax.json.JsonObjectBuilder;
import org.junit.Rule;

public class UserAndTokenHelper {
    
    @Rule
    public JAXRSClientProvider providerLogin = buildWithURI("http://localhost:8080/shopper/api/auth/login");
    public JAXRSClientProvider accountProvider = buildWithURI("http://localhost:8080/shopper/api/account");


    public static String generateUserCredentialsThroughRequest(String user, String password) {
        JsonObject accountToCreate = Json.createObjectBuilder()
                .add("userId", user)
                .add("password", password).build();

        ClientBuilder.newClient().target("http://localhost:8080/shopper/api/account")
                .request()
                .post(Entity.json(accountToCreate));

        return getUserCredential(user,password);
    }

    public static String generateTokenThroughRequest(String user, String password) {
        return ClientBuilder.newClient().target("http://localhost:8080/shopper/api/auth/login")
                .request()
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((generateUserCredentialsThroughRequest(user, password)).getBytes()))
                .get(String.class);
    }
    
    public static String generateToken(String user, String password) {
        return ClientBuilder.newClient().target("http://localhost:8080/shopper/api/auth/login")
                .request()
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((getUserCredential(user, password)).getBytes()))
                .get(String.class);
    }
    
    private static String getUserCredential(String user, String password) {
        return user+":"+password;
    }
    
    public static JsonObject createAccount(String userId, String passord, String choosedhome) {
        JsonObjectBuilder accountBuilder =  Json.createObjectBuilder();
        return accountBuilder.
                add("userId", "shoppertest").
                add("password", "timon").
                add("choosedHome", "ej valt").build();

    }

}
