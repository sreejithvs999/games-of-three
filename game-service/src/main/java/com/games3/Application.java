package com.games3;

import com.games3.domain.events.GameEvent;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean("activemqContainer")
    public GenericContainer<?> activemqContainer() {
        GenericContainer<?> container = new GenericContainer<>("rmohr/activemq")
                .withExposedPorts(61616, 8161)
                .waitingFor(new HostPortWaitStrategy());
        container.setPortBindings(List.of("61616:61616", "8161:8161"));
        container.start();
        return container;
    }

    @Primary
    @Bean
    public ActiveMQProperties activeMQProperties(GenericContainer<?> activemqContainer) {

        val properties = new ActiveMQProperties();
        properties.setInMemory(false);
        return properties;
    }

    @Bean
    public MappingJackson2MessageConverter messageConverter() {
        val converter = new MappingJackson2MessageConverter();
        converter.setTypeIdMappings(Map.of("GameEvent", GameEvent.class));
        converter.setTypeIdPropertyName("messageType");
        return converter;
    }
}
