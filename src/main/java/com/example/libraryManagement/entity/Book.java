package com.example.libraryManagement.entity;

import com.example.libraryManagement.model.AvailabilityStatus;
import com.example.libraryManagement.model.BookRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("author")
    private String author;

    @Column(unique = true)
    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("published_year")
    private int publishedYear;

    @JsonProperty("availability_status")
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    @JsonProperty("book_borrowed_by ")
    private String bookBorrowedBy;


    private boolean isDeleted = false;

    public Book(BookRequest bookRequest) {
        this.title = bookRequest.getTitle();
        this.author = bookRequest.getAuthor();
        this.isbn = bookRequest.getIsbn();
        this.publishedYear = bookRequest.getPublishedYear();
    }

}
