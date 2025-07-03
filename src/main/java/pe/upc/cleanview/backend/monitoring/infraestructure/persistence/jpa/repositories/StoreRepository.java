package com.acme.center.platform.monitoring.infraestructure.persistence.jpa.repositories;

import com.acme.center.platform.monitoring.domain.model.aggregates.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByName(String name);
}
