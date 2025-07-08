package pe.upc.cleanview.backend.tips.application.internal.commandservices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.upc.cleanview.backend.iam.domain.model.aggregates.User;
import pe.upc.cleanview.backend.iam.interfaces.acl.IamContextFacade;
import pe.upc.cleanview.backend.tips.domain.model.aggregates.Favorite;
import pe.upc.cleanview.backend.tips.domain.model.aggregates.SustainableAction;
import pe.upc.cleanview.backend.tips.domain.model.commands.AddFavoriteCommand;
import pe.upc.cleanview.backend.tips.domain.model.commands.RemoveFavoriteCommand;
import pe.upc.cleanview.backend.tips.domain.model.entities.FavoriteId;
import pe.upc.cleanview.backend.tips.domain.services.FavoriteCommandService;
import pe.upc.cleanview.backend.tips.infraestructura.persistence.jpa.repositories.FavoriteRepository;
import pe.upc.cleanview.backend.tips.infraestructura.persistence.jpa.repositories.SustainableActionRepository;

import java.util.Optional;

@Service
public class FavoriteCommandServiceImpl implements FavoriteCommandService {

    private final FavoriteRepository favoriteRepository;
    private final SustainableActionRepository sustainableActionRepository;
    private final IamContextFacade iamContextFacade;

    public FavoriteCommandServiceImpl(FavoriteRepository favoriteRepository,
                                      SustainableActionRepository sustainableActionRepository,
                                      IamContextFacade iamContextFacade) {
        this.favoriteRepository = favoriteRepository;
        this.sustainableActionRepository = sustainableActionRepository;
        this.iamContextFacade = iamContextFacade;
    }

    @Override
    @Transactional
    public Optional<Favorite> handle(AddFavoriteCommand command) {
        User user = iamContextFacade.fetchUserById(command.userId());
        if (user == null) {
            throw new IllegalArgumentException("User with ID " + command.userId() + " not found.");
        }

        Optional<SustainableAction> sustainableActionOptional = sustainableActionRepository.findById(command.sustainableActionId());
        if (sustainableActionOptional.isEmpty()) {
            throw new IllegalArgumentException("Sustainable action with ID " + command.sustainableActionId() + " not found.");
        }

        SustainableAction sustainableAction = sustainableActionOptional.get();

        if (favoriteRepository.existsByIdUserIdAndIdSustainableActionId(command.userId(), command.sustainableActionId())) {
            throw new IllegalArgumentException("Sustainable action with ID " + command.sustainableActionId() + " is already a favorite for user with ID " + command.userId());
        }

        Favorite favorite = new Favorite(user, sustainableAction);
        favoriteRepository.save(favorite);
        return Optional.of(favorite);
    }

    @Override
    @Transactional
    public void handle(RemoveFavoriteCommand command) {

        if (!favoriteRepository.existsByIdUserIdAndIdSustainableActionId(command.userId(), command.sustainableActionId())) {
            throw new IllegalArgumentException("Sustainable action with ID " + command.sustainableActionId() + " is not a favorite for user with ID " + command.userId());
        }
        favoriteRepository.deleteByIdUserIdAndIdSustainableActionId(command.userId(), command.sustainableActionId());
    }
}