package org.example.datamonitoringengine;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"classpath:spring-config.xml"})
@Configurable
@EnableRabbit
public class DataMonitoringEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataMonitoringEngineApplication.class, args);
    }

}
