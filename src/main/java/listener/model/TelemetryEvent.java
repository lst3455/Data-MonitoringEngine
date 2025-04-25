package listener.model;

import lombok.Data;

import java.time.Instant;

@Data
public class TelemetryEvent {

    private String patientId;
    private String deviceId;
    private double heartRate;
    private Instant timestamp;
}

