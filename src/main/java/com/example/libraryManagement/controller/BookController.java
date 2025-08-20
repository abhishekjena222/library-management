package com.example.libraryManagement.controller;

import com.example.libraryManagement.model.BookRequest;
import com.example.libraryManagement.model.JsonResponse;
import com.example.libraryManagement.service.BookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/books")
@Slf4j
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/ping")
    public ResponseEntity<?> ping() {
        log.info("pong");
        return ResponseEntity.ok("pong");
    }

    @PostMapping("/new-book")
    public ResponseEntity<JsonResponse> addBook(@Valid @RequestBody BookRequest book) {
        return bookService.addNewBook(book);
    }

    @GetMapping
    public ResponseEntity<JsonResponse> getBooks(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(required = false) String author,
                                                 @RequestParam(required = false, defaultValue = "0") int publishedYear) {
        log.info("{} {} {} {}", page, size, author, publishedYear);
        return bookService.getAllBooks(page, size, author, publishedYear);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonResponse> getBook(@PathVariable long id) {
        return bookService.getBook(id);

    }

    @PutMapping("/{id}")
    public ResponseEntity<JsonResponse> updateBookDetails(@PathVariable long id, @RequestBody BookRequest bookRequest) throws InterruptedException {
        log.info("id: {}; request: {}", id, bookRequest);
        return bookService.updateBook(id, bookRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JsonResponse> deleteBook(@PathVariable long id) {
        return bookService.deleteBook(id);
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResponse> searchBook(@RequestParam String query) {
        return bookService.searchBook(query);
    }

    @PostMapping("/borrow/{userId}/{bookId}")
    public ResponseEntity<JsonResponse> borrowBook(@PathVariable long userId, @PathVariable long bookId){
        return bookService.borrowBook(userId, bookId);
    }

}
