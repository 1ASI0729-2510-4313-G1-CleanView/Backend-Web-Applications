package pe.upc.cleanview.backend.tips.domain.model.commands;

public record CreateActionCommand(
        String title,
        String description,
        String type,
        boolean favorite) {

}
