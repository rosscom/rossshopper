package se.rosscom.shopper.business;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import java.util.Base64;

public class UserAndTokenHelper {

    public static String generateUserCredentialsThroughRequest() {
        JsonObject accountToCreate = Json.createObjectBuilder()
                .add("userId", "user")
                .add("password", "1234").build();

        ClientBuilder.newClient().target("http://localhost:8080/shopper/api/account")
                .request()
                .post(Entity.json(accountToCreate));

        return "user:1234";
    }

    public static String generateTokenThroughRequest() {
        return ClientBuilder.newClient().target("http://localhost:8080/shopper/api/auth/login")
                .request()
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((generateUserCredentialsThroughRequest()).getBytes()))
                .get(String.class);
    }
}
