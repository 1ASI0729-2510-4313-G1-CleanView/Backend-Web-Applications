package pe.upc.cleanview.backend.monitoring.interfaces.rest.resources;

import java.util.List;

public record StoreResource(
        Long id,
        List<Long> sensorsId,
        String name,
        int numberStore,
        int amountSensor,
        String fillPercent,
        String color,
        String ubication
) {
}
