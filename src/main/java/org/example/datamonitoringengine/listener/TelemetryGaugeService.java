package org.example.datamonitoringengine.listener;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.MultiGauge;
import io.micrometer.core.instrument.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class TelemetryGaugeService {
    private final MultiGauge multiGauge;
    private final Map<String, Double> latest = new ConcurrentHashMap<>();

    public TelemetryGaugeService(MeterRegistry registry) {
        /**
         * If a baseUnit is provided, it’s appended to the metric name.
         * so here micrometer exports to Prometheus as patient_heart_rate_bpm
         */
        this.multiGauge = MultiGauge.builder("patient_heart_rate")
                .description("Simulated patient heart rate")
                .baseUnit("bpm")
                .register(registry);
    }

    public void record(String patientId, double heartRate) {
        latest.put(patientId, heartRate);
        // Build a full list of rows for all patients
        List<MultiGauge.Row<?>> rows = latest.entrySet().stream()
                .map(e -> MultiGauge.Row.of(
                        Tags.of("patient", e.getKey()),
                        e.getValue()
                ))
                .collect(Collectors.toList());
        // Overwrite the MultiGauge with the full set
        multiGauge.register(rows, true);
    }
}

