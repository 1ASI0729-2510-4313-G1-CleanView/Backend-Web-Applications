package pe.upc.cleanview.backend.tips.domain.model.commands;

public record CreateSustainableActionCommand(
        String title,
        String description,
        String type,
        boolean favorite) {

}
