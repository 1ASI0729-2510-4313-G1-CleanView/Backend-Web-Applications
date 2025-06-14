package pe.upc.cleanview.backend.collectionpoints.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.upc.cleanview.backend.collectionpoints.domain.model.aggregates.CollectionPoint;
import pe.upc.cleanview.backend.collectionpoints.domain.model.queries.GetAllCollectionPointsQuery;
import pe.upc.cleanview.backend.collectionpoints.domain.model.queries.GetCollectionPointByIdQuery;
import pe.upc.cleanview.backend.collectionpoints.domain.model.queries.GetCollectionPointByNameQuery;
import pe.upc.cleanview.backend.collectionpoints.domain.services.CollectionPointQueryService;
import pe.upc.cleanview.backend.collectionpoints.infrastructure.persistence.jpa.repositories.CollectionPointRepository;


import java.util.List;
import java.util.Optional;

@Service
public class CollectionPointQueryServiceImpl implements CollectionPointQueryService {

    private final CollectionPointRepository collectionPointRepository;

    public CollectionPointQueryServiceImpl(CollectionPointRepository collectionPointRepository) {
        this.collectionPointRepository = collectionPointRepository;
    }

    @Override
    public List<CollectionPoint> handle(GetAllCollectionPointsQuery query) {
        return collectionPointRepository.findAll();
    }

    @Override
    public Optional<CollectionPoint> handle(GetCollectionPointByIdQuery query) {
        return collectionPointRepository.findById(query.collectionPointId());
    }

    @Override
    public Optional<CollectionPoint> handle(GetCollectionPointByNameQuery query) {
        return collectionPointRepository.findByName(query.name());
    }
}