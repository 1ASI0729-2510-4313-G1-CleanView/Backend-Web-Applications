package pe.upc.cleanview.backend.tips.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.upc.cleanview.backend.tips.domain.model.aggregates.SustainableAction;
import pe.upc.cleanview.backend.tips.domain.model.queries.GetAllSustainableActionTypesQuery;
import pe.upc.cleanview.backend.tips.domain.model.queries.GetSustainableActionsByTypeQuery;
import pe.upc.cleanview.backend.tips.domain.model.queries.GetAllSustainableActionsQuery;
import pe.upc.cleanview.backend.tips.domain.model.valueobjects.SustainableActionType;
import pe.upc.cleanview.backend.tips.domain.services.SustainableActionQueryService;
import pe.upc.cleanview.backend.tips.infraestructura.persistence.jpa.repositories.SustainableActionRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class SustainableSustainableActionQueryServiceImpl implements SustainableActionQueryService {

    private final SustainableActionRepository sustainableActionRepository;

    SustainableSustainableActionQueryServiceImpl(SustainableActionRepository sustainableActionRepository) {
        this.sustainableActionRepository = sustainableActionRepository;
    }

    @Override
    public List<SustainableAction> handle(GetAllSustainableActionsQuery query){
        return sustainableActionRepository.findAll();
    }

    @Override
    public List<SustainableAction>  handle(GetSustainableActionsByTypeQuery query){
        return sustainableActionRepository.findBySustainableActionType(SustainableActionType.fromString(query.type()));
    }

    @Override
    public List<String> handle(GetAllSustainableActionTypesQuery query) {
        return Arrays.stream(SustainableActionType.values())
                .map(Enum::name)
                .toList();
    }

}
