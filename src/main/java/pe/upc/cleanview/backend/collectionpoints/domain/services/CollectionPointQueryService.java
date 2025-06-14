package pe.upc.cleanview.backend.collectionpoints.domain.services;

import pe.upc.cleanview.backend.collectionpoints.domain.model.aggregates.CollectionPoint;
import pe.upc.cleanview.backend.collectionpoints.domain.model.queries.GetAllCollectionPointsQuery;
import pe.upc.cleanview.backend.collectionpoints.domain.model.queries.GetCollectionPointByIdQuery;
import pe.upc.cleanview.backend.collectionpoints.domain.model.queries.GetCollectionPointByNameQuery;

import java.util.List;
import java.util.Optional;

public interface CollectionPointQueryService {
    Optional<CollectionPoint> handle(GetCollectionPointByIdQuery query);
    List<CollectionPoint> handle(GetAllCollectionPointsQuery query);
    Optional<CollectionPoint> handle(GetCollectionPointByNameQuery query);
}
