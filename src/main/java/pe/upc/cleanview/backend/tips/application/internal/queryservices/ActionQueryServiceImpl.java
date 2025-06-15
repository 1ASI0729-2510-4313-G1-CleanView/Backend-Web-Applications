package pe.upc.cleanview.backend.tips.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.upc.cleanview.backend.tips.domain.model.aggregates.Action;
import pe.upc.cleanview.backend.tips.domain.model.queries.GetAllActionsQuery;
import pe.upc.cleanview.backend.tips.domain.services.ActionQueryService;
import pe.upc.cleanview.backend.tips.infraestructura.persistence.jpa.repositories.ActionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ActionQueryServiceImpl implements ActionQueryService {

    private final ActionRepository actionRepository;

    ActionQueryServiceImpl(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Override
    public List<Action> handle(GetAllActionsQuery query){
        return actionRepository.findAll();
    }
}
