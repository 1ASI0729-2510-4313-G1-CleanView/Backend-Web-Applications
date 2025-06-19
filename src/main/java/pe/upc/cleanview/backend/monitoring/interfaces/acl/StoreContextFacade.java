package pe.upc.cleanview.backend.monitoring.interfaces.acl;

import java.util.List;

public interface StoreContextFacade {

    Long createStoreContext(
            List<String> sensorsId,
            String name,
            int storeNumber,
            int amountSensor,
            String fillPercent,
            String color,
            String ubication
    );

}
