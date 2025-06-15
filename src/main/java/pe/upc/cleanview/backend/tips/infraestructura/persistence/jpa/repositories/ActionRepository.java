package pe.upc.cleanview.backend.tips.infraestructura.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.upc.cleanview.backend.tips.domain.model.aggregates.Action;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

}
