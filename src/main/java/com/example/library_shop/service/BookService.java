package com.example.library_shop.service;


import com.example.library_shop.dto.UpdateBookRequest;
import com.example.library_shop.entity.Book;
import com.example.library_shop.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UploadService uploadService;

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book createBook(com.example.library_shop.dto.book.BookRequest request) {
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .image(request.getImage())
                .pricePerDay(request.getPricePerDay())
                .description(request.getDescription())
                .build();

        return bookRepository.save(book);
    }

    public Book updateBook(Long id, UpdateBookRequest request) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Nếu có ảnh mới
        if (request.getImage() != null && !request.getImage().isEmpty()) {

            // 1) XÓA ẢNH CŨ TRÊN CLOUDINARY
            uploadService.deleteImage(book.getImage());

            // 2) UPLOAD ẢNH MỚI
            String newImageUrl = uploadService.uploadImage(request.getImage());
            book.setImage(newImageUrl);
        }

        // UPDATE TEXT FIELDS
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setDescription(request.getDescription());
        book.setPricePerDay(request.getPricePerDay());

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // 1) XÓA ẢNH TRÊN CLOUDINARY
        uploadService.deleteImage(book.getImage());

        // 2) XÓA BOOK TRONG DB
        bookRepository.delete(book);
    }

    // Tổng số sách
    public long countAllBooks() {
        return bookRepository.count();
    }

    // Số sách đang mượn
    public long countBorrowedBooks() {
        return bookRepository.countByStatus("BORROWED");
    }

    // Số sách quá hạn
    public long countOverdueBooks() {
        return bookRepository.countOverdueBooks();
    }


}
