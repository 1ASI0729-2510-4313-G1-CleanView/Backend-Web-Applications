package com.acme.center.platform.monitoring.domain.model.commands;

import java.util.Date;
import java.util.List;

public record UpdateSensorCommand(
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
        String collection
) {
}
