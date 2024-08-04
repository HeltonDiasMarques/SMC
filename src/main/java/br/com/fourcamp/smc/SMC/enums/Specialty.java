package br.com.fourcamp.smc.SMC.enums;

/**
 * Enumeration for medical specialties.
 */
public enum Specialty {
    CARDIOLOGY(0, "Cardiology"),
    DERMATOLOGY(1, "Dermatology"),
    ENDOCRINOLOGY(2, "Endocrinology"),
    GASTROENTEROLOGY(3, "Gastroenterology"),
    NEUROLOGY(4, "Neurology"),
    ONCOLOGY(5, "Oncology"),
    ORTHOPEDICS(6, "Orthopedics"),
    PEDIATRICS(7, "Pediatrics"),
    PSYCHIATRY(8, "Psychiatry"),
    RADIOLOGY(9, "Radiology");

    private final int code;
    private final String description;

    Specialty(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets the code of the specialty.
     *
     * @return the code of the specialty
     */
    public int getCode() {
        return code;
    }

    /**
     * Gets the description of the specialty.
     *
     * @return the description of the specialty
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the specialty from the code.
     *
     * @param code the code of the specialty
     * @return the specialty corresponding to the code
     * @throws IllegalArgumentException if the code is invalid
     */
    public static Specialty fromCode(int code) {
        for (Specialty specialty : Specialty.values()) {
            if (specialty.getCode() == code) {
                return specialty;
            }
        }
        throw new IllegalArgumentException("Invalid specialty code: " + code);
    }

    /**
     * Gets the specialty from the description.
     *
     * @param description the description of the specialty
     * @return the specialty corresponding to the description
     * @throws IllegalArgumentException if the description is invalid
     */
    public static Specialty fromDescription(String description) {
        for (Specialty specialty : Specialty.values()) {
            if (specialty.getDescription().equalsIgnoreCase(description)) {
                return specialty;
            }
        }
        throw new IllegalArgumentException("Invalid specialty description: " + description);
    }
}