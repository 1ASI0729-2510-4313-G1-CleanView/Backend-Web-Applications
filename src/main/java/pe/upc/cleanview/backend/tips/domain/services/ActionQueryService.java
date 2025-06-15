package pe.upc.cleanview.backend.tips.domain.services;

import pe.upc.cleanview.backend.tips.domain.model.aggregates.Action;
import pe.upc.cleanview.backend.tips.domain.model.queries.GetAllActionsQuery;

import java.util.List;

public interface ActionQueryService {
    List<Action> handle(GetAllActionsQuery query);
}
