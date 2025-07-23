package com.example.libraryManagement.service;

import com.example.libraryManagement.entity.Book;
import com.example.libraryManagement.model.AvailabilityStatus;
import com.example.libraryManagement.model.BookRequest;
import com.example.libraryManagement.model.BookResponse;
import com.example.libraryManagement.model.JsonResponse;
import com.example.libraryManagement.repository.BookRepository;
import com.example.libraryManagement.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import static com.example.libraryManagement.Constants.OBJECT_MAPPER;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {


    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private NotificationService notificationService;

    public ResponseEntity<JsonResponse> addNewBook(BookRequest bookRequest) {

        String validationError = validateBook(bookRequest);
        if (validationError != null) {
            log.error("{} in request body : {}", validationError, bookRequest);
            return ResponseUtil.badRequest(validationError);
        }

        try {
            Book book = new Book(bookRequest);
            book.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
            bookRepository.save(book);
            log.info("new book added to library : {}", book);

            BookResponse response = new BookResponse(book);
            return ResponseUtil.created(OBJECT_MAPPER.valueToTree(response));
        } catch (Exception ex) {
            log.error("Error while saving book: {}", ex.getMessage(), ex);
            return ResponseUtil.badRequest(ex.getMessage());
        }

    }

    public ResponseEntity<JsonResponse> getAllBooks(int page, int size, String author, int publishedYear) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Book> books;

        if (author != null && !author.isBlank() && publishedYear != 0) {
            books = bookRepository.findByAuthorContainsAndPublishedYearAndIsDeletedFalseOrderByIdAsc(author, publishedYear, pageable);
        } else if (author != null && !author.isBlank()) {
            books = bookRepository.findByAuthorContainsAndIsDeletedFalseOrderByIdAsc(author, pageable);
        } else if (publishedYear != 0) {
            books = bookRepository.findByPublishedYearAndIsDeletedFalseOrderByIdAsc(publishedYear, pageable);
        } else {
            books = bookRepository.findByIsDeletedFalseOrderByIdAsc(pageable);
        }
        List<BookResponse> bookResponses = books.map(BookResponse::new).getContent();

        return ResponseUtil.success(OBJECT_MAPPER.valueToTree(bookResponses));
    }

    private String validateBook(BookRequest book) {
        if (book.getTitle() == null || book.getTitle().isBlank()) return "Title can't be null or empty";
        if (book.getIsbn() == null || book.getIsbn().isBlank()) return "ISBN can't be null or empty";
        if (book.getAuthor() == null || book.getAuthor().isBlank()) return "Author can't be null or empty";
        if (book.getPublishedYear() < 1500 || book.getPublishedYear() > Year.now().getValue())
            return "Invalid Published Year";
        return null;
    }

    public ResponseEntity<JsonResponse> updateBook(long id, BookRequest bookRequest) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isEmpty() || optionalBook.get().isDeleted()) {
            log.error("Book not found or removed with id: " + id);
            return ResponseUtil.badRequest("data_msg", "Book not found or removed with id: " + id);
        }
        Book book = optionalBook.get();

        AvailabilityStatus oldAvailabilityStatus = book.getAvailabilityStatus();
        AvailabilityStatus newAvailabilityStatus = bookRequest.getAvailabilityStatus();

        if (bookRequest.getAvailabilityStatus() != null)
            book.setAvailabilityStatus(bookRequest.getAvailabilityStatus());
        if (bookRequest.getAuthor() != null && !bookRequest.getAuthor().isBlank())
            book.setAuthor(bookRequest.getAuthor());
        if (bookRequest.getIsbn() != null && !bookRequest.getIsbn().isBlank())
            book.setIsbn(bookRequest.getIsbn());

        int year = bookRequest.getPublishedYear();
        if (year >= 1500 && year <= Year.now().getValue()) book.setPublishedYear(bookRequest.getPublishedYear());

        bookRepository.save(book);

        if (oldAvailabilityStatus == AvailabilityStatus.BORROWED && newAvailabilityStatus == AvailabilityStatus.AVAILABLE) {
            notificationService.sendAvailabilityNotification(book);
        }
        return ResponseUtil.created(OBJECT_MAPPER.valueToTree(book));
    }

    public ResponseEntity<JsonResponse> deleteBook(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty() || optionalBook.get().isDeleted()) {
            log.error("Book not found or removed from inventory");
            return ResponseUtil.badRequest("data_msg", "Book not found or removed from inventory");
        }
        Book book = optionalBook.get();

        book.setDeleted(true);
        bookRepository.save(book);
        log.info("Book removed from inventory : Book: {}", OBJECT_MAPPER.valueToTree(book));
        return ResponseUtil.success("data_msg", "Book removed from inventory : " + OBJECT_MAPPER.valueToTree(book));
    }

    public ResponseEntity<JsonResponse> getBook(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty() || optionalBook.get().isDeleted()) {
            log.error("Book not found");
            return ResponseUtil.badRequest("data_msg", "Book not found");
        }
        Book book = optionalBook.get();
        return ResponseUtil.success(OBJECT_MAPPER.valueToTree(book));

    }

    public ResponseEntity<JsonResponse> searchBook(String query) {
        List<Book> bookList = bookRepository.findByTitleContainsAndIsDeletedFalseOrAuthorContainsAndIsDeletedFalseOrderByIdAsc(query, query);

        if (bookList.isEmpty()) {
            log.error("no book found with query: {}", query);
            return ResponseUtil.badRequest("data_msg", "no book found with query : " + query);
        }
        return ResponseUtil.success(OBJECT_MAPPER.valueToTree(bookList));
    }
}
