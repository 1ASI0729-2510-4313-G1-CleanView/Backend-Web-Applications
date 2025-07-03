package com.acme.center.platform.monitoring.infraestructure.persistence.jpa.repositories;

import com.acme.center.platform.monitoring.domain.model.entities.Waste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteRespository extends JpaRepository<Waste, Long> {
}
