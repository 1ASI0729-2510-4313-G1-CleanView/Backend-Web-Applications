package pe.upc.cleanview.backend.monitoring.infraestructure.persistence.jpa.repositories;

import pe.upc.cleanview.backend.monitoring.domain.model.entities.Waste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteRespository extends JpaRepository<Waste, Long> {
}
