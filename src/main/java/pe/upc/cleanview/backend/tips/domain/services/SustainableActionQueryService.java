package pe.upc.cleanview.backend.tips.domain.services;

import pe.upc.cleanview.backend.tips.domain.model.aggregates.SustainableAction;
import pe.upc.cleanview.backend.tips.domain.model.queries.GetAllSustainableActionTypesQuery;
import pe.upc.cleanview.backend.tips.domain.model.queries.GetSustainableActionsByTypeQuery;
import pe.upc.cleanview.backend.tips.domain.model.queries.GetAllSustainableActionsQuery;

import java.util.List;

public interface SustainableActionQueryService {
    List<SustainableAction> handle(GetAllSustainableActionsQuery query);

    List<SustainableAction> handle(GetSustainableActionsByTypeQuery query);

    List<String> handle(GetAllSustainableActionTypesQuery query);

}
