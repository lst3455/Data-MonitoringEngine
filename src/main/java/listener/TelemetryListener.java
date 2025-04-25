package listener;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.MultiGauge;
import io.micrometer.core.instrument.Tags;
import listener.model.TelemetryEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TelemetryListener {
    private final MultiGauge gauge;
    public TelemetryListener(MeterRegistry registry) {
        gauge = MultiGauge.builder("patient.heart_rate")
                .description("Heart rate per patient")
                .register(registry);
    }

    @RabbitListener(queues="patientMetricsQueue")
    public void onMessage(TelemetryEvent ev) {
        gauge.register(
                List.of(MultiGauge.Row.of(
                        Tags.of("patient", ev.getPatientId()), ev.getHeartRate()
                )),
                true
        );
    }
}

