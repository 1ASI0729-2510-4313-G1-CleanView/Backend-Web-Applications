package pe.upc.cleanview.backend.collectionpoints.domain.model.aggregates;


import jakarta.persistence.*;
import lombok.Getter;
import pe.upc.cleanview.backend.collectionpoints.domain.model.commands.CreateCollectionPointCommand;

import pe.upc.cleanview.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.List;

@Getter
@Entity
public class CollectionPoint extends AuditableAbstractAggregateRoot<CollectionPoint> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String schedule;
    private String phone;
    private Double lat;
    private Double lng;

    @ElementCollection
    private List<String> materials;

    public CollectionPoint() {}

    public CollectionPoint(String name, String schedule, String phone, List<String> materials, Double lat, Double lng) {
        this.name = name;
        this.schedule = schedule;
        this.phone = phone;
        this.materials = materials;
        this.lat = lat;
        this.lng = lng;
    }

    public CollectionPoint(CreateCollectionPointCommand command) {
        this();
        this.name = command.name();
        this.schedule = command.schedule();
        this.phone = command.phone();
        this.materials = command.materials();
        this.lat = command.lat();
        this.lng = command.lng();
    }

    public CollectionPoint updateInformation(String name, String schedule, String phone, List<String> materials, Double lat, Double lng) {
        this.name = name;
        this.schedule = schedule;
        this.phone = phone;
        this.materials = materials;
        this.lat = lat;
        this.lng = lng;
        return this;
    }
}
