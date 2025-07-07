package pe.upc.cleanview.backend.tips.domain.services;

import pe.upc.cleanview.backend.tips.domain.model.aggregates.SustainableAction;
import pe.upc.cleanview.backend.tips.domain.model.queries.GetAllFavoriteActionsByUserIdQuery;
import pe.upc.cleanview.backend.tips.domain.model.queries.IsSustainableActionFavoriteForUserQuery;

import java.util.List;

public interface FavoriteQueryService {
    List<SustainableAction> handle(GetAllFavoriteActionsByUserIdQuery query);
    boolean handle(IsSustainableActionFavoriteForUserQuery query);
}