package pe.upc.cleanview.backend.tips.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import pe.upc.cleanview.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.upc.cleanview.backend.tips.domain.model.commands.CreateActionCommand;
import pe.upc.cleanview.backend.tips.domain.model.valueobjects.ActionType;

@Entity
@Getter
public class Action extends AuditableAbstractAggregateRoot<Action> {
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private boolean favorite;

    public Action() {}

    public Action(CreateActionCommand command) {
        this.title= command.title();
        this.description = command.description();
        this.actionType = ActionType.fromString(command.type());
        this.favorite = command.favorite();
    }

}
