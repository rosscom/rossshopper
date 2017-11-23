package se.rosscom.shopper.business;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.airhacks.rulz.jaxrsclient.JAXRSClientProvider.buildWithURI;

public class EntityHelper {

    private static String accountUri = "http://localhost:8080/shopper/api/account";
    private static String homeUri = "http://localhost:8080/shopper/api/home";
    private static String familyUri = "http://localhost:8080/shopper/api/family";

    public static void deleteAccountByUserId(String userId, String token) {
        JAXRSClientProvider provider = buildWithURI(accountUri);
        provider.client().target(accountUri + "/" + userId).request(MediaType.APPLICATION_JSON).header("Authorization", token).delete();
    }

    public static void deleteHomeByHomeName(String name, String token) {
        JAXRSClientProvider provider = buildWithURI(homeUri);
        provider.client().target(homeUri + "/" + name).request(MediaType.APPLICATION_JSON).header("Authorization", token).delete();
    }

    public static void deleteFamilyByFamilyId(String familyId, String token) {
        JAXRSClientProvider provider = buildWithURI(familyUri);
        provider.client().target(familyUri + "/" + familyId).request(MediaType.APPLICATION_JSON).header("Authorization", token).delete();
    }

    public static void createHomeWithName(String homeName, String token) {
        buildWithURI(homeUri).target().request().header("Authorization", token).post(Entity.json(Json.createObjectBuilder().add("name", homeName).build()));
    }

    public static Integer createFamilyWithHomeNameAndUserId(String homeName, String userId, String token) {
        JsonObject familyToCreate = Json.createObjectBuilder().add("homeName", homeName).add("userId", userId).build();
        Response res = buildWithURI(familyUri).target().request().header("Authorization", token).post(Entity.json(familyToCreate));
        String loc = res.getHeaderString("Location");
        return buildWithURI(familyUri).client().target(loc).request(MediaType.APPLICATION_JSON).header("Authorization", token).get(JsonObject.class).getInt("familyId");
    }
}
