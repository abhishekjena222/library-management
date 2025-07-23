package com.example.libraryManagement.service;

import com.example.libraryManagement.entity.Book;
import com.example.libraryManagement.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Async
    public void sendAvailabilityNotification(Book book){
        wishlistRepository.findAllByBook(book).forEach(wishlist -> {
            log.info("Notification prepared for user_id: {} {} : Book [{}] is now available.",
                    wishlist.getUser().getId(),wishlist.getUser().getEmail(),book.getTitle());
        });
    }
}
