package org.techfrog.mutualtls.client;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.logging.Logger;

@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

    private static final Logger LOGGER = Logger.getLogger(ClientApplication.class.getName());

    @Value("${client.ssl.key-store}")
    private String keyStore;
    @Value("${client.ssl.key-store-password}")
    private String keyStorePassword;
    @Value("${client.ssl.trust-store}")
    private Resource trustStore;
    @Value("${client.ssl.trust-store-password}")
    private String trustStorePassword;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ClientApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    public void run(String... args) throws Exception {
        LOGGER.info("Sending request ... ");
        String response = restTemplate().getForObject("https://localhost:8443/ping", String.class);
        LOGGER.info("Response: " + response);
    }

    /**
     * Configure RestTemplate to accept self-signed certificates
     * @return a RestTemplate instance
     */
    public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, IOException, CertificateException, UnrecoverableKeyException {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new ClassPathResource(keyStore).getInputStream(), keyStorePassword.toCharArray());

        SSLContext sslContext = new SSLContextBuilder()
                .loadKeyMaterial(ks, keyStorePassword.toCharArray())
                .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
                .build();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }
}
