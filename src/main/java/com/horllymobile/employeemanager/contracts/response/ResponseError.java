package com.horllymobile.employeemanager.contracts.response;

public class ResponseError {
    private final String error;

    public ResponseError(String error) {
        this.error = error;
    }

    public String getError(){
        return error;
    }
}
