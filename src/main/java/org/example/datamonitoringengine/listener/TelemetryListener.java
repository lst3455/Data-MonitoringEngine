package org.example.datamonitoringengine.listener;

import org.example.datamonitoringengine.listener.model.TelemetryEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TelemetryListener {

    private final TelemetryGaugeService gaugeService;

    public TelemetryListener(TelemetryGaugeService gaugeService) {
        this.gaugeService = gaugeService;
    }

    @RabbitListener(queues = {
            "${spring.rabbitmq.queue.hr_queue}",
            "${spring.rabbitmq.queue.bp_queue}",
            "${spring.rabbitmq.queue.spo2_queue}",
            "${spring.rabbitmq.queue.rr_queue}",
            "${spring.rabbitmq.queue.temp_queue}"
    })
    public void handleEvent(TelemetryEvent event) {
        log.info("consume message:{}",event.toString());
        gaugeService.recordAll(event);
    }
}

