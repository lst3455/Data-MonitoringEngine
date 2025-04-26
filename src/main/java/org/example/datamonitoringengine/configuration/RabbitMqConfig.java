package org.example.datamonitoringengine.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.queue.hr_queue}")
    private String HR_QUEUE;
    @Value("${spring.rabbitmq.queue.bp_queue}")
    private String BP_QUEUE;
    @Value("${spring.rabbitmq.queue.spo2_queue}")
    private String SPO2_QUEUE;
    @Value("${spring.rabbitmq.queue.rr_queue}")
    private String RR_QUEUE;
    @Value("${spring.rabbitmq.queue.temp_queue}")
    private String TEMP_QUEUE;

    @Bean
    public Declarables rabbitBindings() {
        TopicExchange ex = new TopicExchange("patient.exchange", true, false);
        Queue hrQ = new Queue(HR_QUEUE, true);
        Queue bpQ = new Queue(BP_QUEUE, true);
        Queue spo2Q = new Queue(SPO2_QUEUE, true);
        Queue rrQ = new Queue(RR_QUEUE, true);
        Queue tmpQ = new Queue(TEMP_QUEUE, true);

        Binding bHr = BindingBuilder.bind(hrQ).to(ex).with("patient.metrics.hr.*");
        Binding bBp = BindingBuilder.bind(bpQ).to(ex).with("patient.metrics.bp.*");
        Binding bSpo2 = BindingBuilder.bind(spo2Q).to(ex).with("patient.metrics.spo2.*");
        Binding bRr = BindingBuilder.bind(rrQ).to(ex).with("patient.metrics.rr.*");
        Binding bTmp = BindingBuilder.bind(tmpQ).to(ex).with("patient.metrics.temp.*");

        return new Declarables(ex, hrQ, bpQ, spo2Q, rrQ, tmpQ, bHr, bBp, bSpo2, bRr, bTmp);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(CachingConnectionFactory cf) {
        RabbitAdmin admin = new RabbitAdmin(cf);
        admin.setAutoStartup(true);
        return admin;
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
