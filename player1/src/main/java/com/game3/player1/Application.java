package com.game3.player1;

import com.game3.player1.console.PlayerConsole;
import com.game3.player1.data.GameEvent;
import lombok.val;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import java.util.Map;

@SpringBootApplication()
@EnableJms
public class Application implements ApplicationRunner {


    @Autowired
    private PlayerConsole playerConsole;

    @Autowired
    private ConfigurableApplicationContext ctx;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Primary
    @Bean
    public ActiveMQProperties activeMQProperties() {

        val properties = new ActiveMQProperties();
        properties.setInMemory(false);
        return properties;
    }

    @Bean
    public ActiveMQConnectionFactory receiverActiveMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory();

        return activeMQConnectionFactory;
    }

    @Bean
    public MappingJackson2MessageConverter messageConverter() {
        val converter = new MappingJackson2MessageConverter();
        converter.setTypeIdMappings(Map.of("GameEvent", GameEvent.class));
        converter.setTypeIdPropertyName("messageType");
        return converter;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        playerConsole.run(args);
        ctx.close();
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(receiverActiveMQConnectionFactory());
        factory.setPubSubDomain(true);
        factory.setMessageConverter(messageConverter());
        return factory;
    }
}
