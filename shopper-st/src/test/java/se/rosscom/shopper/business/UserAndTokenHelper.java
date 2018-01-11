package se.rosscom.shopper.business;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import java.util.Base64;

public class UserAndTokenHelper {

    public static WebTarget targetAuth = ClientWrapper.createClient("http://localhost:8080/shopper/api/auth/login");
    public static WebTarget targetAcc = ClientWrapper.createClient("http://localhost:8080/shopper/api/account");

    public static String generateUserCredentialsThroughRequest(String user, String password) {
        JsonObject accountToCreate = Json.createObjectBuilder()
                .add("userId", user)
                .add("password", password).build();

        targetAcc.request().post(Entity.json(accountToCreate));

        return getUserCredential(user,password);
    }

    public static String generateTokenThroughRequest(String user, String password) {
        return targetAuth.request()
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((generateUserCredentialsThroughRequest(user, password)).getBytes()))
                .get(String.class);
    }
    
    public static String generateToken(String user, String password) {
        return targetAuth.request()
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((getUserCredential(user, password)).getBytes()))
                .get(String.class);
    }
    
    private static String getUserCredential(String user, String password) {
        return user+":"+password;
    }

}
