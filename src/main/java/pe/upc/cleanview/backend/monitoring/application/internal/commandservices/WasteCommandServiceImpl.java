package com.acme.center.platform.monitoring.application.internal.commandservices;

import com.acme.center.platform.monitoring.domain.model.commands.CreateWasteCommand;
import com.acme.center.platform.monitoring.domain.model.commands.DeleteWasteCommand;
import com.acme.center.platform.monitoring.domain.model.entities.Waste;
import com.acme.center.platform.monitoring.domain.services.WasteCommandService;
import com.acme.center.platform.monitoring.infraestructure.persistence.jpa.repositories.WasteRespository;
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
