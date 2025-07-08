package pe.upc.cleanview.backend.monitoring.domain.model.queries;

/*
* Query to get the sensors by Store id
* */
public record GetAllSensorByStoreIdQuery(Long storeId) {

    public GetAllSensorByStoreIdQuery {
        if (storeId == null || storeId <= 0) {
            throw new IllegalArgumentException("storeId must be greater than 0");
        }
    }

}
