package pe.upc.cleanview.backend.monitoring.infraestructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.upc.cleanview.backend.monitoring.domain.model.entities.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
