package pe.upc.cleanview.backend.collectionpoints.domain.model.commands;

import java.util.List;

public record UpdateCollectionPointCommand(
        Long id,
        String name,
        String schedule,
        String phone,
        List<String> materials,
        Double lat,
        Double lng
) {}