package com.example.libraryManagement.model;

import com.example.libraryManagement.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonResponse {

    @JsonProperty("status")
    private int status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("error")
    private String error;

    @JsonProperty("data")
    private JsonNode data;

    public JsonResponse(Constants.StatusCode code, JsonNode data) {
        this.status = code.code();
        this.message = code.message();
        this.data = data;
    }

    public JsonResponse(Constants.StatusCode code, String error) {
        this.status = code.code();
        this.message = code.message();
        this.error = error;
    }

    public JsonResponse() {

    }
}
