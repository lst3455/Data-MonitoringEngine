package org.example.datamonitoringengine.listener.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TelemetryEvent {
    /**
     * topic: `patient.metrics.p${id}`,   // routing key
     * payload: {
     *     patientId: id,
     *     metricsList: [{
     *         deviceId: `device.series_${id}`,
     *         heartRate: heartRate,           // bpm
     *         bloodPressure: {
     *             systolic: systolicBP,       // mmHg
     *             diastolic: diastolicBP      // mmHg
     *         },
     *         spo2: spo2,                     // %
     *         respiratoryRate: respiratory,   // breaths/min
     *         temperature: temperature,       // °C
     *         timestamp: new Date().toISOString()
     *     }]
     * }
     */
    private String patientId;
    private String deviceId;
    private List<MonitoringMetrics> metricsList;

}

