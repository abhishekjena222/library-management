package com.example.libraryManagement.service;

import com.example.libraryManagement.entity.Book;
import com.example.libraryManagement.repository.WishlistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class NotificationService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Async
    public void sendAvailabilityNotification(Book book){
        wishlistRepository.findAllByBook(book).forEach(wishlist -> {
            log.info("Notification prepared for user_id: " + wishlist.getUser().getId()
                    + ": Book [" + book.getTitle() + "] is now available.");
        });
    }
}
