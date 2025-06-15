package pe.upc.cleanview.backend.tips.interfaces.rest.transform;

import pe.upc.cleanview.backend.tips.domain.model.aggregates.Action;
import pe.upc.cleanview.backend.tips.interfaces.rest.resources.ActionResource;

public class ActionResourceFromEntityAssembler {
    public static ActionResource toResourceFromEntity(Action entity){
        return new ActionResource(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getActionType().name(),
                entity.isFavorite()
        );
    }
}
