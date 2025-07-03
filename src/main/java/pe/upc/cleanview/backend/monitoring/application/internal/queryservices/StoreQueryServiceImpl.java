package com.acme.center.platform.monitoring.application.internal.queryservices;

import com.acme.center.platform.monitoring.domain.model.aggregates.Store;
import com.acme.center.platform.monitoring.domain.model.queries.GetAllStoresQuery;
import com.acme.center.platform.monitoring.domain.model.queries.GetStoreByIdQuery;
import com.acme.center.platform.monitoring.domain.model.queries.GetStoreByNameQuery;
import com.acme.center.platform.monitoring.domain.services.StoreQueryService;
import com.acme.center.platform.monitoring.infraestructure.persistence.jpa.repositories.StoreRepository;
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
