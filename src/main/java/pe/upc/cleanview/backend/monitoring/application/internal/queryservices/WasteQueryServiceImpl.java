package com.acme.center.platform.monitoring.application.internal.queryservices;

import com.acme.center.platform.monitoring.domain.model.entities.Waste;
import com.acme.center.platform.monitoring.domain.model.queries.GetAllWasteBySensorIdQuery;
import com.acme.center.platform.monitoring.domain.model.queries.GetWasteByIdQuery;
import com.acme.center.platform.monitoring.domain.services.WasteQueryService;
import com.acme.center.platform.monitoring.infraestructure.persistence.jpa.repositories.WasteRespository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WasteQueryServiceImpl implements WasteQueryService {

    private final WasteRespository wasteRespository;

    public WasteQueryServiceImpl(WasteRespository wasteRespository) {
        this.wasteRespository = wasteRespository;
    }

    @Override
    public Optional<Waste> handle(GetWasteByIdQuery query) {
        return wasteRespository.findById(query.id());
    }

    @Override
    public List<Waste> handle(GetAllWasteBySensorIdQuery query) {
        return List.of();
    }

}
