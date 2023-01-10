package com.blanco.crud.utils;

/**
 * The enum messages for responses http.
 */
public enum MessageResponse {

    /**
     *
     * Code 20 - 99 -> Server Errors accompanied with HttpStatus : 5XX
     *
     * Code 0 - 10 --> Success Responses accompanied with HttpStatus 200.
     */

    INTERNAL_ERROR("20", "Internal service error. Try again"), BD_ERROR("21", "Data Base Error."),
    MAP_ENTITY_ERROR("22", "Error mapping Entity"),
    EXIST_PERSON_IN_BD("1", "The person already exists in the database."),
    PERSON_NO_EXIST_IN_BD("2", "The person does not exist in the database"), SUCCESS("0", "Success");

    private String code;
    private String description;

    private MessageResponse(String code, String description) {
	this.code = code;
	this.description = description;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
	return code;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
	return description;
    }

}
