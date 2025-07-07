package pe.upc.cleanview.backend.tips.interfaces.rest.transform;

import pe.upc.cleanview.backend.tips.domain.model.commands.AddFavoriteCommand;
import pe.upc.cleanview.backend.tips.interfaces.rest.resources.AddFavoriteResource;

public class AddFavoriteCommandFromResourceAssembler {
    public static AddFavoriteCommand toCommandFromResource(Long userId, AddFavoriteResource resource) {
        return new AddFavoriteCommand(userId, resource.sustainableActionId());
    }
}