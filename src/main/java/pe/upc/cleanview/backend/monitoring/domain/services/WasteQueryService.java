package pe.upc.cleanview.backend.monitoring.domain.services;

import pe.upc.cleanview.backend.monitoring.domain.model.entities.Waste;
import pe.upc.cleanview.backend.monitoring.domain.model.queries.GetWasteByIdQuery;

import java.util.Optional;

public interface WasteQueryService {

    Optional<Waste> handle(GetWasteByIdQuery query);

}
