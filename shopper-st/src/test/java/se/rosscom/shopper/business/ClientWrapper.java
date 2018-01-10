package se.rosscom.shopper.business;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.Assert;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public class ClientWrapper {

    public static Client createClient() {
        SSLContext ctx = null;

        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("/home/rosscom/clientkeystore"), "vik012vik".toCharArray());

            TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            factory.init(ks);
            TrustManager[] certs = factory.getTrustManagers();

            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, certs, new SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException | KeyManagementException e) {
            e.printStackTrace();
            Assert.fail();
        }

        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        clientBuilder.sslContext(ctx);
        clientBuilder.hostnameVerifier((hostname, session) -> true);

        return clientBuilder.withConfig(new ClientConfig()).build();
    }
}
