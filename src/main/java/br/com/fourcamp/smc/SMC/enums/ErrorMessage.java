package br.com.fourcamp.smc.SMC.enums;

/**
 * Enum representing various error messages and their associated HTTP status codes.
 */
public enum
ErrorMessage {
    //Not Empty
    NAME_CANNOT_BE_EMPTY(400, "Name cannot be empty"),
    EMAIL_CANNOT_BE_EMPTY(400, "Email cannot be empty"),
    PASSWORD_CANNOT_BE_EMPTY(400, "Password cannot be empty"),
    CPF_CANNOT_BE_EMPTY(400, "CPF cannot be empty"),
    BIRTHDATE_CANNOT_BE_EMPTY(400, "Birth date cannot be empty"),
    CEP_CANNOT_BE_EMPTY(400, "CEP cannot be empty"),
    ADDRESS_NUMBER_CANNOT_BE_EMPTY(400, "Address number cannot be empty"),
    PHONE_CANNOT_BE_EMPTY(400, "Phone cannot be empty"),
    PHONE_SPARE_CANNOT_BE_EMPTY(400, "Phone spare cannot be empty"),
    CRM_CANNOT_BE_EMPTY(400, "CRM cannot be empty"),
    SPECIALTY_CANNOT_BE_EMPTY(400, "Specialty cannot be empty"),

    // User related errors
    USER_NOT_FOUND(404, "User not found"),
    INVALID_EMAIL_FORMAT(400, "Invalid email"),
    EMAIL_ALREADY_EXISTS(409, "Email already exists"),
    INVALID_CPF(400, "Invalid CPF"),
    INVALID_ADDRESS(400, "Invalid address"),
    INVALID_CEP_FORMAT(400, "Invalid CEP format"),
    INVALID_AGE(400, "User must be over 18 years old"),
    INVALID_PHONE_FORMAT(400, "Invalid phone number"),
    PHONE_ALREADY_EXISTS(409, "Phone already registered"),
    UNSUPPORTED_USER_TYPE(400, "Unsupported user type"),
    ACCESS_DENIED(403, "Access denied"),

    //Patient related errors
    INVALID_PHONE_SPARE(400, "Invalid phone spare"),
    PHONE_SPARE_EQUAL_TO_PHONE(409, "Phone and phone spare cannot be the same"),

    // Doctor related errors
    INVALID_CRM_FORMAT(400, "Invalid CRM"),
    CRM_ALREADY_REGISTERED(409, "CRM already registered"),

    // Schedule related errors
    SCHEDULE_NOT_FOUND(404, "Schedule not found"),
    SCHEDULE_CONFLICT(409, "Schedule conflict: This schedule already exists for the doctor on the given date and time"),

    CONSULTATION_NOT_FOUND(404, "Consultation not found"),

    // General errors
    INVALID_PASSWORD(401, "Invalid password"),

    // Specific validation errors
    INVALID_DATE_FORMAT(400, "Invalid date format"),
    DATABASE_ERROR(500, "Database error"),
    DATA_INTEGRITY_VIOLATION(409, "Data integrity violation"),
    SQL_EXCEPTION(500,"A SQL error occurred" );

    /**
     * Constructs a new ErrorMessage with the specified status code and message.
     *
     * @param statusCode the HTTP status code associated with the error
     * @param message the error message
     */
    private final int statusCode;
    private final String message;

    ErrorMessage(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    /**
     * Gets the HTTP status code associated with the error.
     *
     * @return the HTTP status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Gets the error message.
     *
     * @return the error message
     */
    public String getMessage() {
        return message;
    }
}