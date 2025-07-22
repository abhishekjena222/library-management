package com.example.libraryManagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "wishlists")
@Data
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Book book;
}
