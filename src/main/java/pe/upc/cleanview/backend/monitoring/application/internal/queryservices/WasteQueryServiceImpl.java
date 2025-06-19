package pe.upc.cleanview.backend.monitoring.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.upc.cleanview.backend.monitoring.domain.model.entities.Waste;
import pe.upc.cleanview.backend.monitoring.domain.model.queries.GetWasteByIdQuery;
import pe.upc.cleanview.backend.monitoring.domain.services.WasteQueryService;
import pe.upc.cleanview.backend.monitoring.infraestructure.persistence.jpa.repositories.WasteRespository;

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

}
