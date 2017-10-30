package se.rosscom.shopper.business.authenticate.boundary;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import static com.airhacks.rulz.jaxrsclient.JAXRSClientProvider.buildWithURI;
import org.junit.Rule;
import org.junit.Test;

public class AuthenticateResourceIT {

    @Rule
    public JAXRSClientProvider provider = buildWithURI("http://localhost:8080/shopper/api/auth");
    
    @Test
    public void crud() {

        // TODO add a test login.
    }

    
}
