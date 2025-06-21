package pe.upc.cleanview.backend.tips.domain.model.valueobjects;

public enum SustainableActionType {
    STORAGE,
    OPERATIONAL,
    REGULATION;
    
    public static SustainableActionType fromString(String str) {
        return switch (str.toLowerCase()){
            case "storage" -> STORAGE;
            case "operational" -> OPERATIONAL;
            case "regulation" -> REGULATION;
            default -> throw new IllegalStateException("Unexpected value: " + str);
        };
    }
}
