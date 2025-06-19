package pe.upc.cleanview.backend.monitoring.interfaces.acl;

import java.util.Date;
import java.util.List;

public interface SensorContextFacade {

    Long createSensorContext(
            String serialNumber,
            List<Long> wastesId,
            String location,
            String status,
            int battery,
            Date lastModified,
            String units,
            String capacity,
            String currentCapacity,
            String percentage,
            String collection
    );

}
