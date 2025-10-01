package com.oraclejava.library;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.http.HttpHost;

import org.apache.hc.core5.ssl.SSLContexts;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.httpclient5.ApacheHttpClient5TransportBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class OpenSearchConfig {

    @Value("${spring.opensearch.uri}")
    private String host;

    @Value("${spring.opensearch.username}")
    private String username;

    @Value("${spring.opensearch.password}")
    private String password;

    @Bean
    public OpenSearchClient openSearchClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        HttpHost httpHost = new HttpHost( "https", host, 9200);
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(new AuthScope(httpHost),
                new UsernamePasswordCredentials(username, password.toCharArray()));

        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(null, TrustAllStrategy.INSTANCE) // 모든 인증서 신뢰
                .build();

        var tlsStrategy = ClientTlsStrategyBuilder.create()
                .setSslContext(sslContext)
                .setHostnameVerifier(NoopHostnameVerifier.INSTANCE).buildAsync(); // 호스트 검증 끔


        ApacheHttpClient5TransportBuilder builder = ApacheHttpClient5TransportBuilder.builder(httpHost);

        builder.setHttpClientConfigCallback(httpClientBuilder
                -> {
                    var connManager = PoolingAsyncClientConnectionManagerBuilder.create()
                    .setTlsStrategy(tlsStrategy)
                    .build();
                    return httpClientBuilder
                            .setDefaultCredentialsProvider(credentialsProvider)
                            .setConnectionManager(connManager);

                });
        OpenSearchTransport openSearchTransport = builder.build();
        return new OpenSearchClient(openSearchTransport);
    }
}
