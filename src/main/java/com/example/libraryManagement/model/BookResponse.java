package com.example.libraryManagement.model;

import com.example.libraryManagement.entity.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("author")
    private String author;
    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("published_year")
    private int publishedYear;

    @JsonProperty("availability_status")
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
        this.publishedYear = book.getPublishedYear();
        this.availabilityStatus = book.getAvailabilityStatus();
    }



}
