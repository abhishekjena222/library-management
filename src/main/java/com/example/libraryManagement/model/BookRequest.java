package com.example.libraryManagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookRequest {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("author")
    private String author;
    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("published_year")
    private int publishedYear = 0;

    @JsonProperty("availability_status")
    private AvailabilityStatus availabilityStatus;

}
