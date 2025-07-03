package com.acme.center.platform.monitoring.domain.model.entities;

import com.acme.center.platform.monitoring.domain.model.aggregates.Sensor;
import com.acme.center.platform.monitoring.domain.model.commands.CreateWasteCommand;
import com.acme.center.platform.monitoring.domain.model.valueobjects.WasteType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Entity
@Getter
public class Waste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Cannot be empty")
    private String name;

    @Embedded
    private WasteType type;

    @Column(nullable = false)
    @Min(0)
    private int amount;

    public Waste(CreateWasteCommand command) {
        this.name = command.name();
        this.type = new WasteType(command.type());
        this.amount = command.amount();
    }

    public Waste() {}

}
