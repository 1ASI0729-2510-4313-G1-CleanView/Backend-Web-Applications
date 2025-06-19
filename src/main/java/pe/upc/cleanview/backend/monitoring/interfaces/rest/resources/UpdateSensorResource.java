package pe.upc.cleanview.backend.monitoring.interfaces.rest.resources;

import java.util.Date;
import java.util.List;

public record UpdateSensorResource(
        Long id,
        String serialNumber,
        List<Long> wastesId,
        String location,
        String status,
        int battery,
        Date lastModified,
        String units,
        String capacity,
        String currentCapacity,
        String percentage,
        String collection
) {
}
