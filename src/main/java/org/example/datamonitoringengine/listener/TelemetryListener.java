package org.example.datamonitoringengine.listener;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.MultiGauge;
import io.micrometer.core.instrument.Tags;
import org.example.datamonitoringengine.listener.model.TelemetryEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

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
        gaugeService.record(event.getPatientId(), event.getHeartRate());
    }
}

