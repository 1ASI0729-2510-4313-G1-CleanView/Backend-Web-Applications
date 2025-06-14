package pe.upc.cleanview.backend.collectionpoints.infrastructure.persistence.jpa.repositories;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.upc.cleanview.backend.collectionpoints.domain.model.aggregates.CollectionPoint;

import java.util.Optional;

@Repository
public interface CollectionPointRepository extends JpaRepository<CollectionPoint, Long> {
    // Validaciones y búsqueda por nombre
    boolean existsByName(String name);
    boolean existsByNameAndIdIsNot(String name, Long id);
    Optional<CollectionPoint> findByName(String name);
}
