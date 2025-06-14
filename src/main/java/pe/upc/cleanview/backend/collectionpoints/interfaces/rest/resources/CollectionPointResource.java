package pe.upc.cleanview.backend.collectionpoints.interfaces.rest.resources;



import java.util.List;

public record CollectionPointResource(
        Long id,
        String name,
        String schedule,
        String phone,
        List<String> materials,
        double lat,
        double lng
) {}
