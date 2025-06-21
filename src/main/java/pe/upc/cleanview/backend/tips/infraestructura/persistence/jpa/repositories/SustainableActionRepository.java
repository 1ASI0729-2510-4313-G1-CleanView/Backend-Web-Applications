package pe.upc.cleanview.backend.tips.infraestructura.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.upc.cleanview.backend.tips.domain.model.aggregates.SustainableAction;
import pe.upc.cleanview.backend.tips.domain.model.valueobjects.SustainableActionType;

import java.util.List;

@Repository
public interface SustainableActionRepository extends JpaRepository<SustainableAction, Long> {

    List<SustainableAction> findBySustainableActionType(SustainableActionType type);
}
