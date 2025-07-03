package com.acme.center.platform.monitoring.infraestructure.persistence.jpa.repositories;

import com.acme.center.platform.monitoring.domain.model.aggregates.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
