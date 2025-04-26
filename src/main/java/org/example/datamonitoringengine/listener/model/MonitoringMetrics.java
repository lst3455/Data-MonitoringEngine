package org.example.datamonitoringengine.listener.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)  // ignore any extra JSON props
public class MonitoringMetrics {
    /**
     * following property may be null for different queue from different device
     */
    private BloodPressure bloodPressure;
    private Double heartRate;
    private Double spo2;
    private Double respiratoryRate;
    private Double temperature;
    private Instant timestamp;
}
