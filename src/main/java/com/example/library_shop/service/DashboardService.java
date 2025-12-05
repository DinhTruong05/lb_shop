package com.example.library_shop.service;

import com.example.library_shop.dto.DashboardSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final BookService bookService;

    public DashboardSummaryDTO getSummary() {
        long total = bookService.countAllBooks();
        long borrowed = bookService.countBorrowedBooks();
        long overdue = bookService.countOverdueBooks();

        return new DashboardSummaryDTO(total, borrowed, overdue);
    }
}
