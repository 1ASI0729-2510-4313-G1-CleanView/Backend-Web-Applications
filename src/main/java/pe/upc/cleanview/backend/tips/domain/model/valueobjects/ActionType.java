package pe.upc.cleanview.backend.tips.domain.model.valueobjects;

public enum ActionType {
    STORAGE,
    OPERATIONAL,
    REGULATION;
    
    public static ActionType fromString(String str) {
        return switch (str.toLowerCase()){
            case "storage" -> STORAGE;
            case "operational" -> OPERATIONAL;
            case "regulation" -> REGULATION;
            default -> throw new IllegalStateException("Unexpected value: " + str);
        };
    }
}
