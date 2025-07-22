package com.example.libraryManagement.repository;

import com.example.libraryManagement.entity.Book;
import com.example.libraryManagement.entity.WishList;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<WishList, Long> {

    @NonNull
    List<WishList> findAllByBook(Book book);

    Optional<WishList> findByUser_IdAndBook_Id(long id, Long id1);
}
