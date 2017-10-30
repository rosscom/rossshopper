package se.rosscom.shopper.business.authenticate.boundary;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import static com.airhacks.rulz.jaxrsclient.JAXRSClientProvider.buildWithURI;
import java.util.Base64;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;

public class AuthenticateResourceIT {

    @Rule
    public JAXRSClientProvider provider = buildWithURI("http://localhost:8080/shopper/api/auth/login");
    public JAXRSClientProvider accountProvider = buildWithURI("http://localhost:8080/shopper/api/account");
    
    @Test
    public void login() {
        String basicAuthString =  "Basic " + Base64.getEncoder().encodeToString(("1:1234").getBytes());
        String token = this.provider.target()
                .request()
                .header("Authorization", basicAuthString)
                .get(String.class);
        assertThat(token, startsWith("Auth-shopper"));

        Response securedResponse = this.accountProvider.target()
                .request()
                .header("Authorization", token)
                .get();
        assertThat(securedResponse.getStatus(), is(200));
    }
}
