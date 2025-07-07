package pe.upc.cleanview.backend.tips.infraestructura.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.upc.cleanview.backend.tips.domain.model.aggregates.Favorite;
import pe.upc.cleanview.backend.tips.domain.model.entities.FavoriteId;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {

    Optional<Favorite> findByIdUserIdAndIdSustainableActionId(Long userId, Long sustainableActionId);

    List<Favorite> findByIdUserId(Long userId);

    void deleteByIdUserIdAndIdSustainableActionId(Long userId, Long sustainableActionId);

    boolean existsByIdUserIdAndIdSustainableActionId(Long userId, Long sustainableActionId);
}