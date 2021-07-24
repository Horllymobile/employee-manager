package com.horllymobile.employeemanager.contracts.response;


public class CreateEmployeeResponse {
    private final String message;

    public CreateEmployeeResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
