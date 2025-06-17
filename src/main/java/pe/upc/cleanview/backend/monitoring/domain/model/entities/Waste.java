package pe.upc.cleanview.backend.monitoring.domain.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.CreateWasteCommand;

@Entity
@Getter
public class Waste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int amount;

    public Waste(CreateWasteCommand command) {
        this.name = command.name();
        this.type = command.type();
        this.amount = command.amount();
    }

    public Waste() {}

}
