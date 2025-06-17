package pe.upc.cleanview.backend.monitoring.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.CreateStoreCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.UpdateStoreCommand;
import pe.upc.cleanview.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;


import java.util.List;

@Entity
@Getter
public class Store extends AuditableAbstractAggregateRoot<Store> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<Long> sensorsId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @JoinColumn(name = "store_number")
    private int storeNumber;

    @JoinColumn(name = "amount_sensor")
    private int amountSensor;

    @JoinColumn(name = "fill_percent")
    private String fillPercent;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String ubication;


    public Store(CreateStoreCommand command){
        this.sensorsId = command.sensorsId();
        this.name = command.name();
        this.storeNumber = command.storeNumber();
        this.amountSensor = command.amountSensor();
        this.fillPercent = command.fillPercent();
        this.color = command.color();
        this.ubication = command.ubication();
    }

    public Store Update(UpdateStoreCommand command){
        this.id = command.id();
        this.sensorsId = command.sensorsId();
        this.name = command.name();
        this.storeNumber = command.storeNumber();
        this.amountSensor = command.amountSensor();
        this.fillPercent = command.fillPercent();
        this.color = command.color();
        this.ubication = command.ubication();
        return this;
    }


    public Store() {}
}
