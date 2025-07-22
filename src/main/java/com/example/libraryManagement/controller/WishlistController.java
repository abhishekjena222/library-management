package com.example.libraryManagement.controller;

import com.example.libraryManagement.model.JsonResponse;
import com.example.libraryManagement.service.WishlistService;
import com.example.libraryManagement.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<JsonResponse> addWishlist(@PathVariable long userId, @PathVariable long bookId){
        log.info(" {} {}",userId,bookId);
        return wishlistService.addToWishlist(userId,bookId);
    }

}
