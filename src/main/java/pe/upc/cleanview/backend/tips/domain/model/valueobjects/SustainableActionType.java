package pe.upc.cleanview.backend.tips.domain.model.valueobjects;

/**
 * Enumeration representing the types of sustainable actions.
 * @summary
 * This enumeration is used to classify sustainable actions into specific types.
 * It helps indicate the category of each sustainable action.
 * The possible values are STORAGE, OPERATIONAL, and REGULATION.
 * @since 1.0
 */
public enum SustainableActionType {
    STORAGE,
    OPERATIONAL,
    REGULATION;

    /**
     * Converts a string to its corresponding SustainableActionType.
     * @param str the string representation of the action type.
     * @return the corresponding SustainableActionType.
     * @throws IllegalStateException if the value cannot be matched.
     */
    public static SustainableActionType fromString(String str) {
        return switch (str.toLowerCase()) {
            case "storage" -> STORAGE;
            case "operational" -> OPERATIONAL;
            case "regulation" -> REGULATION;
            default -> throw new IllegalStateException("Unexpected value: " + str);
        };
    }
}