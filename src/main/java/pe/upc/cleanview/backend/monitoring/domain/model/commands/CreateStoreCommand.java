package pe.upc.cleanview.backend.monitoring.domain.model.commands;



public record CreateStoreCommand(
        String name,
        int numberStore,
        int amountSensor,
        String fillPercent,
        String color,
        String ubication
) {}