package com.popcornblog.movies.adapters.in.web.exceptions.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    UNABLE_TO_READ_REQUEST_BODY("/unable-to-read-request-body", "Unable to read request body."),
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found."),
    ENTITY_IN_USE("/entity-in-use", "Entity in use."),
    BUSINESS_EXCEPTION("/business-exception", "Business rule violation."),
    INVALID_PARAMETER("/invalid-parameter", "Invalid parameter."),
    SYSTEM_ERROR("/system-error", "Internal server error."),
    INVALID_DATA("/invalid-data", "Invalid data");

    private String title;
    private String uri;

    ProblemType(String path, String title){
        this.uri = "https://blogpopcorn.com" + path;
        this.title = title;
    }
}