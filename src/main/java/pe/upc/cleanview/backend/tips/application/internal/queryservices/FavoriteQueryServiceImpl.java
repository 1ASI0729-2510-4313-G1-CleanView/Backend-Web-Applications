package pe.upc.cleanview.backend.tips.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.upc.cleanview.backend.tips.domain.model.aggregates.Favorite;
import pe.upc.cleanview.backend.tips.domain.model.aggregates.SustainableAction;
import pe.upc.cleanview.backend.tips.domain.model.queries.GetAllFavoriteActionsByUserIdQuery;
import pe.upc.cleanview.backend.tips.domain.model.queries.IsSustainableActionFavoriteForUserQuery;
import pe.upc.cleanview.backend.tips.domain.services.FavoriteQueryService;
import pe.upc.cleanview.backend.tips.infraestructura.persistence.jpa.repositories.FavoriteRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteQueryServiceImpl implements FavoriteQueryService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteQueryServiceImpl(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public List<SustainableAction> handle(GetAllFavoriteActionsByUserIdQuery query) {

        List<Favorite> favorites = favoriteRepository.findByIdUserId(query.userId());

        return favorites.stream()
                .map(Favorite::getSustainableAction)
                .collect(Collectors.toList());
    }

    @Override
    public boolean handle(IsSustainableActionFavoriteForUserQuery query) {
        return favoriteRepository.existsByIdUserIdAndIdSustainableActionId(query.userId(), query.sustainableActionId());
    }
}