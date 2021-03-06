package se.rosscom.shopper.business;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class EntityHelper {

    private static String accountUri = "http://localhost:8080/shopper/api/account";
    private static String homeUri = "http://localhost:8080/shopper/api/home";
    private static String familyUri = "http://localhost:8080/shopper/api/family";

    public static void deleteAccountByUserId(String userId, String token) {
        ClientWrapper.createClient(accountUri + "/" + userId).request(MediaType.APPLICATION_JSON).header("Authorization", token).delete();
    }

    public static void deleteHomeByHomeName(String name, String token) {
        ClientWrapper.createClient(homeUri + "/" + name).request(MediaType.APPLICATION_JSON).header("Authorization", token).delete();
    }

    public static void deleteFamilyByFamilyId(String familyId, String token) {
        ClientWrapper.createClient(familyUri + "/" + familyId).request(MediaType.APPLICATION_JSON).header("Authorization", token).delete();
    }

    public static void createHomeWithName(String homeName, String token) {
        ClientWrapper.createClient(homeUri).request().header("Authorization", token).post(Entity.json(Json.createObjectBuilder().add("name", homeName).build()));
    }

    public static Integer createFamilyWithHomeNameAndUserId(String homeName, String userId, String token) {
        JsonObject familyToCreate = Json.createObjectBuilder().add("homeName", homeName).add("userId", userId).build();
        Response res = ClientWrapper.createClient(familyUri).request().header("Authorization", token).post(Entity.json(familyToCreate));
        String loc = res.getHeaderString("Location");
        return ClientWrapper.createClient(loc).request(MediaType.APPLICATION_JSON).header("Authorization", token).get(JsonObject.class).getInt("familyId");
    }
}
