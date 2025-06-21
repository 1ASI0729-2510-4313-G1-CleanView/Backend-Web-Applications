package pe.upc.cleanview.backend.tips.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import pe.upc.cleanview.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.upc.cleanview.backend.tips.domain.model.commands.CreateSustainableActionCommand;
import pe.upc.cleanview.backend.tips.domain.model.valueobjects.SustainableActionType;

@Entity
@Getter
public class SustainableAction extends AuditableAbstractAggregateRoot<SustainableAction> {
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private SustainableActionType sustainableActionType;

    private boolean favorite;

    public SustainableAction() {}

    public SustainableAction(CreateSustainableActionCommand command) {
        this.title= command.title();
        this.description = command.description();
        this.sustainableActionType = SustainableActionType.fromString(command.type());
        this.favorite = command.favorite();
    }

}
