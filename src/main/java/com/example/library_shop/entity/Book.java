package com.example.library_shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 500)
    private String description;

    private String author;

    private String image; // URL ảnh bìa

    private Integer pricePerDay; // Giá thuê theo ngày

    private String status;

    private LocalDate dueDate;
}
