package com.example.libraryManagement.repository;

import com.example.libraryManagement.entity.Book;
import com.example.libraryManagement.entity.User;
import com.example.libraryManagement.entity.WishList;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<WishList, Long> {

    @NonNull
    List<WishList> findAllByBook(Book book);

    Optional<WishList> findByUser_IdAndBook_Id(long id, Long id1);

    @Transactional
    @Modifying
    @Query("delete from WishList w where w.user = ?1")
    void deleteByUser(User user);

}
