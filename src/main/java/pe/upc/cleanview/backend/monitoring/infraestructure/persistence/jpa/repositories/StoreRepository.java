package pe.upc.cleanview.backend.monitoring.infraestructure.persistence.jpa.repositories;

import pe.upc.cleanview.backend.monitoring.domain.model.aggregates.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByName(String name);

}
