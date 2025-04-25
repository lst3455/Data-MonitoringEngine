package org.example.datamonitoringengine.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.queue.patient_metric_queue}")
    private String queueName;

    @Bean
    public Queue patientMetricsQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public TopicExchange patientExchange() {
        return new TopicExchange("patient.exchange", true, false);
    }

    @Bean
    public Binding bindPatient(Queue patientMetricsQueue, TopicExchange patientExchange) {
        return BindingBuilder.bind(patientMetricsQueue)
                .to(patientExchange)
                .with("patient.metrics.*");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        // Register the JavaTimeModule to handle Instant, LocalDate, etc.
        mapper.registerModule(new JavaTimeModule());
        // OPTIONAL: write dates as ISO strings rather than timestamps
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return new Jackson2JsonMessageConverter(mapper);
    }
}
