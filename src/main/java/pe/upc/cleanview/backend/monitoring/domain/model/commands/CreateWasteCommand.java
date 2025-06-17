package pe.upc.cleanview.backend.monitoring.domain.model.commands;

public record CreateWasteCommand(
        String name,
        String type,
        int amount
) {
}
