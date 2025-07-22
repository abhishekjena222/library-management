package com.example.libraryManagement.util;

import com.example.libraryManagement.Constants;
import com.example.libraryManagement.model.JsonResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static com.example.libraryManagement.Constants.OBJECT_MAPPER;

public class ResponseUtil {

    public static ResponseEntity<JsonResponse> badRequest(String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        return ResponseEntity.badRequest()
                .body(new JsonResponse(Constants.StatusCode.VALIDATION_ERROR, OBJECT_MAPPER.valueToTree(map)));
    }

    public static ResponseEntity<JsonResponse> badRequest(String message) {
        return ResponseEntity.badRequest().body(new JsonResponse(Constants.StatusCode.VALIDATION_ERROR, message));
    }

    public static ResponseEntity<JsonResponse> created(JsonNode jsonNode) {
        return ResponseEntity.status(201).body(new JsonResponse(Constants.StatusCode.CREATED, jsonNode));
    }

    public static ResponseEntity<JsonResponse> success(JsonNode jsonNode) {
        return ResponseEntity.ok(new JsonResponse(Constants.StatusCode.SUCCESS, jsonNode));
    }

    public static ResponseEntity<JsonResponse> success(String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        return ResponseEntity.ok(new JsonResponse(Constants.StatusCode.SUCCESS, OBJECT_MAPPER.valueToTree(map)));
    }

    public static ResponseEntity<JsonResponse> build(Constants.StatusCode statusCode, JsonNode jsonNode) {
        return ResponseEntity.ok(new JsonResponse(statusCode, jsonNode));
    }


}
