package se.rosscom.shopper.business;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.Assert;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public class ClientWrapper {

    public static WebTarget createClient(String url) {

        //TODO Here we need something to determine where the it-tests run. dev or prd...
        if (new File("/home/rosscom/clientkeystore").exists()) {

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

            //TODO awful way of handling different urls... works for now. maybe env-properties file?
            String httpsString = new StringBuilder(url).insert(4, "s").toString();

            return clientBuilder.withConfig(new ClientConfig()).build().target(httpsString);
        } else {
            return ClientBuilder.newClient().target(url);
        }
    }
}
