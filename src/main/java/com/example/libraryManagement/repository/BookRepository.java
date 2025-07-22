package com.example.libraryManagement.repository;

import com.example.libraryManagement.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByAuthorContainsAndPublishedYearAndIsDeletedFalseOrderByIdAsc(String author, int publishedYear, Pageable pageable);

    Page<Book> findByAuthorContainsAndIsDeletedFalseOrderByIdAsc(String author, Pageable pageable);

    Page<Book> findByPublishedYearAndIsDeletedFalseOrderByIdAsc(int publishedYear, Pageable pageable);

    Page<Book> findByIsDeletedFalseOrderByIdAsc(Pageable pageable);

    List<Book> findByTitleContainsAndIsDeletedFalseOrAuthorContainsAndIsDeletedFalseOrderByIdAsc(String title, String author);

}
