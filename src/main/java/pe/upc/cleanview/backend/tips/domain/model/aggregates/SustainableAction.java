package pe.upc.cleanview.backend.tips.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;
import pe.upc.cleanview.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.upc.cleanview.backend.tips.domain.model.commands.CreateSustainableActionCommand;
import pe.upc.cleanview.backend.tips.domain.model.valueobjects.SustainableActionType;

/**
 * SustainableAction aggregate root.
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

    /**
     * Flag indicating if this sustainable action is marked as favorite.
     */
    private boolean favorite;

    /**
     * Default constructor for JPA.
     */
    protected SustainableAction() {
        this.title = Strings.EMPTY;
        this.description = Strings.EMPTY;
        this.sustainableActionType = null;
        this.favorite = false;
    }

    /**
     * Creates a new sustainable action with the given data.
     * @param command the command containing the sustainable action information
     */
    public SustainableAction(CreateSustainableActionCommand command) {
        this.title = command.title();
        this.description = command.description();
        this.sustainableActionType = SustainableActionType.fromString(command.type());
        this.favorite = command.favorite();
    }

    /**
     * Marks this sustainable action as favorite.
     * @summary Changes the state of this action to favorite.
     */
    public void markAsFavorite() {
        this.favorite = true;
    }

    /**
     * Unmarks this sustainable action as favorite.
     * @summary Changes the state of this action to not favorite.
     */
    public void unmarkAsFavorite() {
        this.favorite = false;
    }
}