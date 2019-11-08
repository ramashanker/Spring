package com.rama.spring.app.configuration;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import java.security.KeyStore;

@Configuration
public class ServiceConfig {
    @Value("${truststore.file.path}")
    String truststorefile;
    @Value("${truststore.password}")
    String truststorePassword;
    @Value("${client.file.path}")
    String clientfile;
    @Value("${client.password}")
    String clientPassword;
    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    public RestTemplate restTemplate() throws Exception  {
        KeyStore clientStore = KeyStore.getInstance("JKS");
        clientStore.load(loadTrustStore().getInputStream(), truststorePassword.toCharArray());
        clientStore.load(loadClientFile().getInputStream(), clientPassword.toCharArray());
        SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
        sslContextBuilder.useProtocol("TLS");
        sslContextBuilder.loadKeyMaterial(clientStore, "certpassword".toCharArray());
        sslContextBuilder.loadTrustMaterial(new TrustSelfSignedStrategy());

        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build());
        CloseableHttpClient httpClient = HttpClients.custom()
                                                    .setSSLSocketFactory(sslConnectionSocketFactory)
                                                    .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        requestFactory.setConnectTimeout(10000); // 10 seconds
        requestFactory.setReadTimeout(10000); // 10 seconds
        return new RestTemplate(requestFactory);
    }

    public Resource loadTrustStore() {
        return new ClassPathResource(truststorefile);
    }

    public Resource loadClientFile() {
        return new ClassPathResource(clientfile);
    }
}
