package com.acme.center.platform.monitoring.domain.model.aggregates;

import com.acme.center.platform.monitoring.domain.model.commands.CreateStoreCommand;
import com.acme.center.platform.monitoring.domain.model.commands.UpdateStoreCommand;
import com.acme.center.platform.monitoring.domain.model.entities.Waste;
import com.acme.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Store extends AuditableAbstractAggregateRoot<Store> {

    @ManyToMany
    @JoinTable(
            name = "bridge_store_sensor",
            joinColumns = @JoinColumn(name = "store_id"),
            inverseJoinColumns = @JoinColumn(name = "sensor_id")
    )
    private List<Sensor> sensorsId;

    @Column(nullable = false)
    @NotBlank(message = "Cannot be empty")
    private String name;

    @Column(name = "store_number", nullable = false)
    @Min(0)
    private int storeNumber;

    /*
     * Min of sensor 0
     * */
    @Column(name = "amount_sensor", nullable = false)
    @Min(0)
    private int amountSensor;


    /*
     * range of 0-100 percent including the % symbol
     * */
    @Column(name = "fill_percent")
    @Pattern(regexp = "^(100|[1-9]?\\d)%$")
    private String fillPercent;

    /*
     * request a hexadecimal code for color
     * */
    @Column(nullable = false)
    @NotBlank(message = "Cannot be empty")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "It must be a valid hexadecimal color, like #FFFFFF or #ff5733")
    private String color;

    /*
     * Valid if it´s null and dont allow the empty text
     * min length 1 && max 50
     * */
    @Column(nullable = false)
    @NotBlank(message = "Cannot be empty")
    @Size(min = 1, max = 50)
    private String ubication;


    public Store(CreateStoreCommand command){
        this.name = command.name();
        this.storeNumber = command.storeNumber();
        this.amountSensor = command.amountSensor();
        this.fillPercent = command.fillPercent();
        this.color = command.color();
        this.ubication = command.ubication();
    }

    public Store Update(UpdateStoreCommand command){
        this.name = command.name();
        this.storeNumber = command.storeNumber();
        this.amountSensor = command.amountSensor();
        this.fillPercent = command.fillPercent();
        this.color = command.color();
        this.ubication = command.ubication();
        return this;
    }


    public Store() {}

    public void addSensorToStore(List<Sensor> sensors){
        this.sensorsId.clear();
        this.sensorsId = sensors;
        System.out.println(" -- [ Sensor added to store" + this.sensorsId.stream().map(Sensor::getId).toList());
    }

}
