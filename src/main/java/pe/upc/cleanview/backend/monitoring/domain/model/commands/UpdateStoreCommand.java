package pe.upc.cleanview.backend.monitoring.domain.model.commands;


import java.util.List;

public record UpdateStoreCommand(
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
