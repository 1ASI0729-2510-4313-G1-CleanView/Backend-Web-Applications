package pe.upc.cleanview.backend.monitoring.application.internal.queryservices;

import pe.upc.cleanview.backend.monitoring.domain.model.aggregates.Store;
import pe.upc.cleanview.backend.monitoring.domain.model.queries.GetAllStoresQuery;
import pe.upc.cleanview.backend.monitoring.domain.model.queries.GetStoreByIdQuery;
import pe.upc.cleanview.backend.monitoring.domain.model.queries.GetStoreByNameQuery;
import pe.upc.cleanview.backend.monitoring.domain.services.StoreQueryService;
import pe.upc.cleanview.backend.monitoring.infraestructure.persistence.jpa.repositories.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreQueryServiceImpl implements StoreQueryService {

    private final StoreRepository storeRepository;

    StoreQueryServiceImpl(StoreRepository storeRepository) {this.storeRepository = storeRepository;}

    @Override
    public Optional<Store> handle(GetStoreByIdQuery query) {
        return storeRepository.findById(query.id());
    }

    @Override
    public Optional<Store> handle(GetStoreByNameQuery query) {
        return storeRepository.findByName(query.name());
    }

    @Override
    public List<Store> handle(GetAllStoresQuery query) {
        return storeRepository.findAll();
    }

}
