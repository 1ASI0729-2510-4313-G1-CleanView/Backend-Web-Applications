package com.acme.center.platform.monitoring.interfaces.rest.resources;

import java.util.Date;
import java.util.List;

public record CreateSensorResource(
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

    public CreateSensorResource {
        if (serialNumber == null || serialNumber.isBlank()) throw new IllegalArgumentException("serialNumber cannot be blank");
        if (location == null || location.isBlank()) throw new IllegalArgumentException("location cannot be blank");
        if (battery < 0) throw new IllegalArgumentException("battery must be non-negative");
        if (lastDetection == null) throw new IllegalArgumentException("lastModified cannot be null");
        if (units == null || units.isBlank()) throw new IllegalArgumentException("units cannot be blank");
        if (capacity == null || capacity.isBlank()) throw new IllegalArgumentException("capacity cannot be blank");
        if (currentCapacity == null || currentCapacity.isBlank()) throw new IllegalArgumentException("currentCapacity cannot be blank");
        if (percentage == null || percentage.isBlank()) throw new IllegalArgumentException("percentage cannot be blank");
        if (collection == null || collection.isBlank()) throw new IllegalArgumentException("collection cannot be blank");
    }

}
