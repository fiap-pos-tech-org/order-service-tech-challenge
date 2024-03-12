package br.com.fiap.techchallenge.servicopedido.adapters.message.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

import java.net.URI;

@Configuration
public class SNSConfig {

    @Value("${localstack.endpoint}")
    private String ENDPOINT;

    @Bean
    @Profile("local")
    public SnsClient snsLocalClient() {
        return SnsClient.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create(ENDPOINT))
                .build();
    }

    @Bean
    @Profile("cloud")
    public SnsClient snsClient() {
        return SnsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(WebIdentityTokenFileCredentialsProvider.create())
                .build();
    }
}
