package se.rosscom.shopper.business;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;

import static com.airhacks.rulz.jaxrsclient.JAXRSClientProvider.buildWithURI;

public class EntityHelper {

    public static void deleteAccountByUserId(String userId, String token) {
        String uri = "http://localhost:8080/shopper/api/account";
        JAXRSClientProvider provider = buildWithURI(uri);
        provider.client().target(uri + "/" + userId).request(MediaType.APPLICATION_JSON).header("Authorization", token).delete();
    }

    public static void deleteHomeByHomeName(String name, String token) {
        String uri = "http://localhost:8080/shopper/api/home";
        JAXRSClientProvider provider = buildWithURI(uri);
        provider.client().target(uri + "/" + name).request(MediaType.APPLICATION_JSON).header("Authorization", token).delete();
    }

    public static void deleteFamilyByFamilyId(String familyId, String token) {
        String uri = "http://localhost:8080/shopper/api/family";
        JAXRSClientProvider provider = buildWithURI(uri);
        provider.client().target(uri + "/" + familyId).request(MediaType.APPLICATION_JSON).header("Authorization", token).delete();
    }
}
