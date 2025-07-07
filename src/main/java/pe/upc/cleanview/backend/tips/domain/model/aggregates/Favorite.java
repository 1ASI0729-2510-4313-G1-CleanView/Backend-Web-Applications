package pe.upc.cleanview.backend.tips.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.upc.cleanview.backend.iam.domain.model.aggregates.User;
import pe.upc.cleanview.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.upc.cleanview.backend.tips.domain.model.entities.FavoriteId;

/**
 * Favorite aggregate root. Represents a favorite sustainable action for a specific user.
 */
@Entity
@Getter
@Setter
@Table(name = "favorites")
public class Favorite {

    @EmbeddedId
    private FavoriteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("sustainableActionId")
    @JoinColumn(name = "sustainable_action_id")
    private SustainableAction sustainableAction;

    protected Favorite() {}

    public Favorite(User user, SustainableAction sustainableAction) {
        this.user = user;
        this.sustainableAction = sustainableAction;
        this.id = new FavoriteId(user.getId(), sustainableAction.getId());
    }
}