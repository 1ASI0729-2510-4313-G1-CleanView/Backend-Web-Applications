package pe.upc.cleanview.backend.monitoring.infraestructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.upc.cleanview.backend.monitoring.domain.model.entities.Waste;

public interface WasteRespository extends JpaRepository<Waste, Long> {
}
