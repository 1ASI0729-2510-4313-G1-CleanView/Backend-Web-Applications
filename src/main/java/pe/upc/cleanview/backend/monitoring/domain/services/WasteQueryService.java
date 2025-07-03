package com.acme.center.platform.monitoring.domain.services;

import com.acme.center.platform.monitoring.domain.model.entities.Waste;
import com.acme.center.platform.monitoring.domain.model.queries.GetAllWasteBySensorIdQuery;
import com.acme.center.platform.monitoring.domain.model.queries.GetWasteByIdQuery;

import java.util.List;
import java.util.Optional;

public interface WasteQueryService {

    Optional<Waste> handle(GetWasteByIdQuery query);

    List<Waste> handle(GetAllWasteBySensorIdQuery query);

}
