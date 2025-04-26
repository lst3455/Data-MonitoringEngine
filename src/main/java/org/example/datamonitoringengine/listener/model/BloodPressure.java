package org.example.datamonitoringengine.listener.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodPressure {
    private Double systolic;
    private Double diastolic;
}
