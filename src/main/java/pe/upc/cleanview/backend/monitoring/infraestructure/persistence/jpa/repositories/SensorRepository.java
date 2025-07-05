package pe.upc.cleanview.backend.monitoring.infraestructure.persistence.jpa.repositories;

import pe.upc.cleanview.backend.monitoring.domain.model.aggregates.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
