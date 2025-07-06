package pe.upc.cleanview.backend.monitoring.domain.model.valueobjects;

public record WasteType(String type){

    public WasteType(){this(null);}

    public String getWasteType(){ return this.type; }

    public WasteType{
        if (type == null || type.isBlank())
            throw new IllegalArgumentException("WasteType cannot be null or blank");
    }
};
