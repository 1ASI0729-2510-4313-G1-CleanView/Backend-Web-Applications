package pe.upc.cleanview.backend.monitoring.application.internal.commandservices;

import pe.upc.cleanview.backend.monitoring.domain.model.commands.CreateWasteCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.DeleteWasteCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.entities.Waste;
import pe.upc.cleanview.backend.monitoring.domain.services.WasteCommandService;
import pe.upc.cleanview.backend.monitoring.infraestructure.persistence.jpa.repositories.WasteRespository;
import org.springframework.stereotype.Service;

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

    @Override
    public void handle(DeleteWasteCommand command) {
        var id = command.id();
        var waste = wasteRespository.findById(id);
        if (waste.isEmpty())
            throw new IllegalArgumentException("Waste not found");

        try {
            wasteRespository.delete(waste.get());
        }catch (Exception e) {
            throw new IllegalArgumentException("Error deleting waste", e);
        }
    }
}
