package pe.upc.cleanview.backend.tips.domain.services;

import pe.upc.cleanview.backend.tips.domain.model.aggregates.Favorite;
import pe.upc.cleanview.backend.tips.domain.model.commands.AddFavoriteCommand;
import pe.upc.cleanview.backend.tips.domain.model.commands.RemoveFavoriteCommand;

import java.util.Optional;

public interface FavoriteCommandService {
    Optional<Favorite> handle(AddFavoriteCommand command);
    void handle(RemoveFavoriteCommand command);
}