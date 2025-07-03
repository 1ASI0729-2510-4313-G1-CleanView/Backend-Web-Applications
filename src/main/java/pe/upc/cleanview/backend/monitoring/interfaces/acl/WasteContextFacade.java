package com.acme.center.platform.monitoring.interfaces.acl;

public interface WasteContextFacade {

    Long createWasteContext(
            String name,
            String type,
            int amount
    );

}
