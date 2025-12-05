package com.example.library_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardSummaryDTO {
    private long totalBooks;      // Tổng số sách
    private long borrowedBooks;   // Sách đang mượn
    private long overdueBooks;    // Sách quá hạn
}
