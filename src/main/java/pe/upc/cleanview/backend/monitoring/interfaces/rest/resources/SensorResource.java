package com.acme.center.platform.monitoring.interfaces.rest.resources;

import java.util.Date;
import java.util.List;

public record SensorResource(
        Long id,
        List<Long> wastesId,
        String serialNumber,
        String location,
        int battery,
        Date lastDetection,
        String units,
        String capacity,
        String currentCapacity,
        String percentage,
        String collection,
        String sensorStatus
) {
}
