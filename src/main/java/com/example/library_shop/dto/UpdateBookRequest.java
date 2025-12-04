package com.example.library_shop.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateBookRequest {
    private String title;
    private String author;
    private Integer pricePerDay;
    private String description;
    private MultipartFile image; // ảnh mới (optional)
}