package org.example.datamonitoringengine.listener;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.MultiGauge;
import io.micrometer.core.instrument.Tags;
import org.example.datamonitoringengine.listener.model.TelemetryEvent;
import org.example.datamonitoringengine.listener.model.MonitoringMetrics;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class TelemetryGaugeService {
    private final MultiGauge hrGauge;
    private final MultiGauge bpSystolicGauge;
    private final MultiGauge bpDiastolicGauge;
    private final MultiGauge spo2Gauge;
    private final MultiGauge respGauge;
    private final MultiGauge tempGauge;

    // Store latest values per patient-device for each metric
    private final Map<String, Double> latestHr = new ConcurrentHashMap<>();
    private final Map<String, Double> latestBpSys = new ConcurrentHashMap<>();
    private final Map<String, Double> latestBpDia = new ConcurrentHashMap<>();
    private final Map<String, Double> latestSpo2 = new ConcurrentHashMap<>();
    private final Map<String, Double> latestResp = new ConcurrentHashMap<>();
    private final Map<String, Double> latestTemp = new ConcurrentHashMap<>();

    public TelemetryGaugeService(MeterRegistry registry) {
        hrGauge = MultiGauge.builder("patient_heart_rate_bpm").register(registry);
        bpSystolicGauge = MultiGauge.builder("patient_blood_pressure_systolic_mmHg").register(registry);
        bpDiastolicGauge = MultiGauge.builder("patient_blood_pressure_diastolic_mmHg").register(registry);
        spo2Gauge = MultiGauge.builder("patient_spo2_percent").register(registry);
        respGauge = MultiGauge.builder("patient_respiratory_rate_bpm").register(registry);
        tempGauge = MultiGauge.builder("patient_temperature_celsius").register(registry);
    }

    public void recordAll(TelemetryEvent ev) {
        String pid = ev.getPatientId();
        // Iterate each metrics entry
        for (MonitoringMetrics m : ev.getMetricsList()) {
            String key = pid + ":" + m.getDeviceId();
            if (m.getHeartRate() != null) {
                latestHr.put(key, m.getHeartRate());
            }
            if (m.getBloodPressure() != null) {
                latestBpSys.put(key, m.getBloodPressure().getSystolic());
                latestBpDia.put(key, m.getBloodPressure().getDiastolic());
            }
            if (m.getSpo2() != null) {
                latestSpo2.put(key, m.getSpo2());
            }
            if (m.getRespiratoryRate() != null) {
                latestResp.put(key, m.getRespiratoryRate());
            }
            if (m.getTemperature() != null) {
                latestTemp.put(key, m.getTemperature());
            }
        }
        // After updating maps, rebuild rows for each metric
        rebuild(hrGauge, latestHr);
        rebuild(bpSystolicGauge, latestBpSys);
        rebuild(bpDiastolicGauge, latestBpDia);
        rebuild(spo2Gauge, latestSpo2);
        rebuild(respGauge, latestResp);
        rebuild(tempGauge, latestTemp);
    }

    private void rebuild(MultiGauge gauge, Map<String, Double> latestMap) {
        List<MultiGauge.Row<?>> rows = latestMap.entrySet().stream()
                .map(e -> {
                    // split the key back into patient and device
                    String[] parts = e.getKey().split(":");
                    return MultiGauge.Row.of(
                            Tags.of("patient", parts[0], "device", parts[1]),
                            e.getValue()
                    );
                })
                .collect(Collectors.toList());
        gauge.register(rows, true);
    }
}
