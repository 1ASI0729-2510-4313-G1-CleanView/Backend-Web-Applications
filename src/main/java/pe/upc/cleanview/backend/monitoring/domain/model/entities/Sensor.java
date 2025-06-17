package pe.upc.cleanview.backend.monitoring.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.CreateSensorCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.UpdateSensorCommand;
import pe.upc.cleanview.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Sensor extends AuditableAbstractAggregateRoot<Sensor> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String serialNumber;

    @ElementCollection
    private List<Long> wastesId;

    @Column(nullable = false)
    private String location;

    private String status;

    private int battery;

    @LastModifiedDate
    private Date lastModified;

    @Column(nullable = false)
    private String units;

    @Column(nullable = false)
    private String capacity;

    private String currentCapacity;

    private String percentage;

    private String collection;

    public Sensor(CreateSensorCommand command) {
        this.serialNumber = command.serialNumber();
        this.wastesId = command.wastesId();
        this.location = command.location();
        this.status = command.status();
        this.battery = command.battery();
        this.lastModified = command.lastModified();
        this.units = command.units();
        this.capacity = command.capacity();
        this.currentCapacity = command.currentCapacity();
        this.percentage = command.percentage();
        this.collection = command.collection();
    }

    public Sensor Update(UpdateSensorCommand command) {
        this.id = command.id();
        this.serialNumber = command.serialNumber();
        this.wastesId = command.wastesId();
        this.location = command.location();
        this.status = command.status();
        this.battery = command.battery();
        this.lastModified = command.lastModified();
        this.units = command.units();
        this.capacity = command.capacity();
        this.currentCapacity = command.currentCapacity();
        this.percentage = command.percentage();
        this.collection = command.collection();
        return this;
    }

    public Sensor(){}
}
