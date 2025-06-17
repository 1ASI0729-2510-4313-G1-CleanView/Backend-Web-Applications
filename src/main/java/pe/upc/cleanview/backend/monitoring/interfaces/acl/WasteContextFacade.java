package pe.upc.cleanview.backend.monitoring.interfaces.acl;

public interface WasteContextFacade {

    Long createWasteContext(
            String name,
            String type,
            int amount
    );

}
