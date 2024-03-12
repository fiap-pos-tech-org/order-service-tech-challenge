package br.com.fiap.techchallenge.servicopedido.adapters.message.config;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import jakarta.jms.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
@EnableJms
public class JMSConfig {

    private final SqsClient sqsClient;

    public JMSConfig(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Bean
    public ConnectionFactory sqsConnectionFactory() {
        return new SQSConnectionFactory(new ProviderConfiguration(), sqsClient);
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        var factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(sqsConnectionFactory());
        factory.setDestinationResolver(new DynamicDestinationResolver());
        return factory;
    }
}
