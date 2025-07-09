package pe.upc.cleanview.backend.monitoring.domain.model.commands;

import java.util.Date;
import java.util.List;

public record CreateSensorCommand(
        List<Long> wastesId,
         String serialNumber,
         String location,
         int battery,
         String units,
         String capacity,
         String currentCapacity,
         String percentage,
         String collection
) {
}
