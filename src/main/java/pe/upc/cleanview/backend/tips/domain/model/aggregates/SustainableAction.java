package pe.upc.cleanview.backend.tips.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;
import pe.upc.cleanview.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.upc.cleanview.backend.tips.domain.model.valueobjects.SustainableActionType;

import java.util.HashSet;
import java.util.Set;

/**
 * SustainableAction aggregate root.
 *
 * @summary
 * Represents a sustainable action that can be managed within the platform.
 * A sustainable action contains a title, a description, a sustainable action type,
 * and a flag indicating whether it is marked as favorite.
 * @see SustainableActionType
 * @since 1.0
 */
@Entity
@Getter
public class SustainableAction extends AuditableAbstractAggregateRoot<SustainableAction> {

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private SustainableActionType sustainableActionType;

    private Long creatorUserId;

    @OneToMany(mappedBy = "sustainableAction")
    private Set<Favorite> favoritedBy = new HashSet<>();

    /**
     * Default constructor for JPA.
     */
    protected SustainableAction() {
        this.title = Strings.EMPTY;
        this.description = Strings.EMPTY;
        this.sustainableActionType = null;
        this.creatorUserId = null;
    }

    /**
     * Creates a new sustainable action with the given data.
     * @param title The title of the action.
     * @param description The description of the action.
     * @param type The type of the action (String representation of SustainableActionType).
     * @param creatorUserId The ID of the user who created this action.
     */

    public SustainableAction(String title, String description, String type, Long creatorUserId) {
        this.title = title;
        this.description = description;
        this.sustainableActionType = SustainableActionType.fromString(type);
        this.creatorUserId = creatorUserId;
    }


}
