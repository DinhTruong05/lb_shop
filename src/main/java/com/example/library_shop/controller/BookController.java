package com.example.library_shop.controller;


import com.example.library_shop.dto.UpdateBookRequest;
import com.example.library_shop.entity.Book;
import com.example.library_shop.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@CrossOrigin(
        origins = "http://localhost:5173",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS}
)
public class BookController {

    private final BookService bookService;

    // GET ALL
    @GetMapping
    public List<Book> getAll() {
        return bookService.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    // CREATE BOOK (JSON)
    @PostMapping
    public Book create(@RequestBody @Valid com.example.library_shop.dto.book.BookRequest request) {
        return bookService.createBook(request);
    }

    // UPDATE BOOK (multipart/form-data)
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public Book updateBook(
            @PathVariable Long id,
            @ModelAttribute UpdateBookRequest request
    ) {
        return bookService.updateBook(id, request);
    }

    // DELETE BOOK
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "Deleted successfully";
    }
}
