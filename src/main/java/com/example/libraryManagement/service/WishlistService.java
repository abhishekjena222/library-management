package com.example.libraryManagement.service;

import com.example.libraryManagement.entity.WishList;
import com.example.libraryManagement.model.JsonResponse;
import com.example.libraryManagement.repository.BookRepository;
import com.example.libraryManagement.repository.UserRepository;
import com.example.libraryManagement.repository.WishlistRepository;
import com.example.libraryManagement.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    public ResponseEntity<JsonResponse> addToWishlist(long userId, long bookId) {

        if (!bookRepository.existsById(bookId)) {
            log.error("Book not available with ID: " + bookId);
            return ResponseUtil.badRequest("Book not available with ID: " + bookId);

        }

        if (!userRepository.existsById(userId)) {
            log.error("User not available with ID: " + userId);
            return ResponseUtil.badRequest("User not available with ID: " + userId);
        }

        if (wishlistRepository.findByUser_IdAndBook_Id(userId, bookId).isEmpty()) {
            log.error("Already added in wishlist");
            return ResponseUtil.badRequest("Already added in wishlist");
        }

        WishList wishList = new WishList();
        wishList.setBook(bookRepository.findById(bookId).get());
        wishList.setUser(userRepository.findById(userId).get());

        wishlistRepository.save(wishList);

        log.info("book Id:{} added to wishlist for user id:{}", bookId, userId);
        return ResponseUtil.success("data_msg", "added book to wishlist");
    }
}
