package pe.upc.cleanview.backend.monitoring.domain.services;

import pe.upc.cleanview.backend.monitoring.domain.model.aggregates.Store;
import pe.upc.cleanview.backend.monitoring.domain.model.queries.GetAllStoresQuery;
import pe.upc.cleanview.backend.monitoring.domain.model.queries.GetStoreByIdQuery;
import pe.upc.cleanview.backend.monitoring.domain.model.queries.GetStoreByNameQuery;

import java.util.List;
import java.util.Optional;

public interface StoreQueryService {

    Optional<Store> handle(GetStoreByIdQuery query);

    Optional<Store> handle(GetStoreByNameQuery query);

    List<Store> handle(GetAllStoresQuery query);

}
