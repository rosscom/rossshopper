package se.rosscom.shopper.business;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.Test;
import se.rosscom.shopper.business.UserAndTokenHelper;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.net.ssl.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SSLTest {

    @Test
    public void testSSLConnection() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {

        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream("/home/rosscom/clientkeystore"), "vik012vik".toCharArray());

        TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init(ks);
        TrustManager[] certs = factory.getTrustManagers();
//        TrustManager[] certs = new TrustManager[]{new X509TrustManager() {
//            @Override
//            public X509Certificate[] getAcceptedIssuers() {
//                return null;
//            }
//
//            @Override
//            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//            }
//
//            @Override
//            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//            }
//        }};

        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, certs, new SecureRandom());
        } catch (java.security.GeneralSecurityException e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }

        HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());

        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        try {
            clientBuilder.sslContext(ctx);
            clientBuilder.hostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }

        Client client = clientBuilder.withConfig(new ClientConfig()).build();


        String token = client.target("https://localhost:8080/shopper/api/auth/login")
                .request()
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(("user:psw").getBytes()))
                .get(String.class);

        System.out.println("######################### After, login.....");

        JsonObjectBuilder homeBuilder =  Json.createObjectBuilder();
        JsonObject homeToCreate = homeBuilder.
                add("name", "dagg").
                add("adress", "daggstigen 20").build();

        Response postResponse = client.target("https://localhost:8080/shopper/api/home").request().header("Authorization", token).post(Entity.json(homeToCreate));;
        assertThat(postResponse.getStatus(),is(201));
        System.out.println(postResponse.getHeaderString("Location"));
    }
}
