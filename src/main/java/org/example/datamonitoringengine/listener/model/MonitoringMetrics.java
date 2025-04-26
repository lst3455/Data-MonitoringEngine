package org.example.datamonitoringengine.listener.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonitoringMetrics {
    private String      deviceId;
    private BloodPressure bloodPressure;   // may be null for some metrics sets
    private Double      heartRate;        // may be null if not present
    private Double      spo2;
    private Double      respiratoryRate;
    private Double      temperature;
    private Instant     timestamp;
}
