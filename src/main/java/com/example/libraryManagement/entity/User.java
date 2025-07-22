package com.example.libraryManagement.entity;

import com.example.libraryManagement.model.UserRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(unique = true)
    private String email;

    public User(UserRequest userRequest) {
        this.email = userRequest.getEmail();
        this.name = userRequest.getName();
    }
}
