package se.rosscom.shopper.business.authenticate.boundary;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import static com.airhacks.rulz.jaxrsclient.JAXRSClientProvider.buildWithURI;
import java.util.Base64;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;

public class AuthenticateResourceIT {

    @Rule
    public JAXRSClientProvider provider = buildWithURI("http://localhost:8080/shopper/api/auth/login");
    
    @Test
    public void login() {

        // TODO correct this login.
        String authToken = Base64.getEncoder().encodeToString(("admin:nilsudden").getBytes());
        
        JsonObjectBuilder authBuilder = Json.createObjectBuilder();
        authBuilder.add("Authentication", authToken);
        
        JsonObject authLogin = authBuilder.build();
        
        Response response = this.provider.target().
                request().
                post(Entity.json(authLogin));
        assertThat(response.getStatus(),is(201));
 

    }
}
