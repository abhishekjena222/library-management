package com.example.libraryManagement;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface Constants {


    ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    enum StatusCode {
        SUCCESS(200, "Success"),
        ACCEPTABLE(202, "Acceptable"),
        CREATED(201, "Created"),
        VALIDATION_ERROR(400, "Validation failed"),
        NOT_FOUND(404, "Resource not found"),
        SERVER_ERROR(500, "Internal server error");

        private final int code;
        private final String message;

        StatusCode(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int code() {
            return code;
        }

        public String message() {
            return message;
        }
    }
}
