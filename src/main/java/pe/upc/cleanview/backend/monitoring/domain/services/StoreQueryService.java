package com.acme.center.platform.monitoring.domain.services;

import com.acme.center.platform.monitoring.domain.model.aggregates.Store;
import com.acme.center.platform.monitoring.domain.model.queries.GetAllStoresQuery;
import com.acme.center.platform.monitoring.domain.model.queries.GetStoreByIdQuery;
import com.acme.center.platform.monitoring.domain.model.queries.GetStoreByNameQuery;

import java.util.List;
import java.util.Optional;

public interface StoreQueryService {

    Optional<Store> handle(GetStoreByIdQuery query);

    Optional<Store> handle(GetStoreByNameQuery query);

    List<Store> handle(GetAllStoresQuery query);

}
