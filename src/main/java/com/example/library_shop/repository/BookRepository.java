package com.example.library_shop.repository;

import com.example.library_shop.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

    long countByStatus(String status);

    // Đếm số sách quá hạn (dueDate < hôm nay) và status vẫn đang mượn
    @Query("SELECT COUNT(b) FROM Book b WHERE b.dueDate < CURRENT_DATE AND b.status = 'BORROWED'")
    long countOverdueBooks();
}
