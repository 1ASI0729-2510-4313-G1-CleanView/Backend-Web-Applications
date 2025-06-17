package pe.upc.cleanview.backend.monitoring.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.CreateWasteCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.entities.Waste;
import pe.upc.cleanview.backend.monitoring.domain.services.WasteCommandService;
import pe.upc.cleanview.backend.monitoring.infraestructure.persistence.jpa.repositories.WasteRespository;

import java.util.Optional;

@Service
public class WasteCommandServiceImpl implements WasteCommandService {

    private final WasteRespository wasteRespository;

    WasteCommandServiceImpl(WasteRespository wasteRespository) {
        this.wasteRespository = wasteRespository;
    }

    @Override
    public Optional<Waste> handle(CreateWasteCommand command) {
        var waste = new Waste(command);
        wasteRespository.save(waste);
        return Optional.of(waste);
    }
}
