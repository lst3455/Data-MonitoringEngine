package org.example.datamonitoringengine.listener;

import org.example.datamonitoringengine.listener.model.TelemetryEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TelemetryListener {

    private final TelemetryGaugeService gaugeService;

    public TelemetryListener(TelemetryGaugeService gaugeService) {
        this.gaugeService = gaugeService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.patient_metric_queue}")
    public void handleEvent(TelemetryEvent event) {
        log.info("consume message:{}",event.toString());
        gaugeService.recordAll(event);
    }
}

