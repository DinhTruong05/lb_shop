package com.example.library_shop.dto.book;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {

    @NotBlank(message = "Tên sách không được để trống")
    private String title;

    @NotBlank(message = "Tác giả không được để trống")
    private String author;

    @NotBlank(message = "Ảnh không được để trống")
    private String image;

    @NotNull(message = "Giá thuê không được để trống")
    @Min(value = 1000, message = "Giá thuê phải từ 1000đ trở lên")
    private Integer pricePerDay;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;
}
