package pe.upc.cleanview.backend.monitoring.domain.model.aggregates;

import pe.upc.cleanview.backend.monitoring.domain.model.commands.CreateSensorCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.UpdateSensorCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.entities.Waste;
import pe.upc.cleanview.backend.monitoring.domain.model.valueobjects.SensorStatus;
import pe.upc.cleanview.backend.monitoring.domain.model.valueobjects.WasteType;
import pe.upc.cleanview.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Table(name = "sensors")
public class Sensor extends AuditableAbstractAggregateRoot<Sensor> {

    @Column(nullable = false)
    private String serialNumber;

    @ManyToMany
    @JoinTable(
            name = "bridge_sensor_waste",
            joinColumns = @JoinColumn(name = "sensor_id"),
            inverseJoinColumns = @JoinColumn(name = "waste_id")
    )
    private List<Waste> wastesId;

    @Column(nullable = false)
    @NotBlank(message = "Cannot be empty")
    private String location;

    private SensorStatus status;

    @Column(nullable = false)
    @Min(0)
    private int battery;

    @LastModifiedDate
    private Date lastDetection;

    @Column(nullable = false)
    @NotBlank(message = "Cannot be empty")
    private String units;

    @Column(nullable = false)
    @NotBlank(message = "Cannot be empty")
    private String capacity;

    private String currentCapacity;

    private String percentage;

    private String collection;

    public Sensor(CreateSensorCommand command) {
        this.serialNumber = command.serialNumber();
        this.location = command.location();
        this.status = SensorStatus.ACTIVE;
        this.battery = command.battery();
        this.units = command.units();
        this.capacity = command.capacity();
        this.currentCapacity = command.currentCapacity();
        this.percentage = command.percentage();
        this.collection = command.collection();
    }

    public Sensor Update(UpdateSensorCommand command) {
        this.serialNumber = command.serialNumber();
        this.location = command.location();
        this.battery = command.battery();
        this.lastDetection = command.lastDetection();
        this.units = command.units();
        this.capacity = command.capacity();
        this.currentCapacity = command.currentCapacity();
        this.percentage = command.percentage();
        this.collection = command.collection();
        return this;
    }

    public void active() { this.status = SensorStatus.ACTIVE; }

    public void disabled() { this.status = SensorStatus.DISABLED; }

    public void maintenance() { this.status = SensorStatus.MAINTENANCE; }

    public String getStatus() {
        return status.name();
    }

    public Sensor(){}

    public void addWastesToSensor(List<Waste>wastes){
        if (this.wastesId == null) {
            this.wastesId = new ArrayList<>();
        } else {
            this.wastesId.clear();
        }
        this.wastesId.addAll(wastes);
        System.out.println(" -- [[ Wastes added : " + wastes.size());
        System.out.println(" -- [[ Wastes added IDs : " + wastes.stream().map(Waste::getId).toList());
    }
}
