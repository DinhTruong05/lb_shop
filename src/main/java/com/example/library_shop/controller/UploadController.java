package com.example.library_shop.controller;

import com.example.library_shop.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UploadController {

    private final UploadService uploadService;

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file) {
        return uploadService.uploadImage(file);
    }
}
