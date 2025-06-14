package pe.upc.cleanview.backend.collectionpoints.domain.model.commands;

import java.util.List;

public record CreateCollectionPointCommand(
        String name,
        String schedule,
        String phone,
        List<String> materials,
        Double lat,
        Double lng
) {}