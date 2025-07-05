package pe.upc.cleanview.backend.monitoring.domain.model.queries;


/*
* Query to get all waste by sensor id
* */
public record GetAllWasteBySensorIdQuery(Long sensorId) {

    public GetAllWasteBySensorIdQuery {
        if (sensorId == null || sensorId <= 0) {
            throw new IllegalArgumentException("Sensor id must be greater than or equal to 0");
        }
    }

}
