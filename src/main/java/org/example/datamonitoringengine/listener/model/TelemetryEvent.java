package org.example.datamonitoringengine.listener.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelemetryEvent {

    private String patientId;
    private String deviceId;
    private double heartRate;
    private Instant timestamp;
}

