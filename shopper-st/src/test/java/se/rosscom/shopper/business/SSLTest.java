package se.rosscom.shopper.business;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.Test;
import javax.net.ssl.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;

import static org.junit.Assert.assertTrue;

public class SSLTest {

    public void testSSLConnection() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, KeyManagementException {

        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream("/home/rosscom/clientkeystore"), "vik012vik".toCharArray());

        TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init(ks);
        TrustManager[] certs = factory.getTrustManagers();

        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, certs, new SecureRandom());

        HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());

        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        clientBuilder.sslContext(ctx);
        clientBuilder.hostnameVerifier((hostname, session) -> true);

        Client client = clientBuilder.withConfig(new ClientConfig()).build();


        String token = client.target("https://localhost:8080/shopper/api/auth/login")
                .request()
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(("user:psw").getBytes()))
                .get(String.class);

        assertTrue(token != null);
        System.out.println("######################### Token: + " + token + " , from login-request");
    }
}
