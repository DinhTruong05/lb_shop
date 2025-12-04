package com.example.library_shop.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final Cloudinary cloudinary;

    // UPLOAD ẢNH
    public String uploadImage(MultipartFile file) {
        try {
            String folder = "books/";

            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder,
                            "resource_type", "image"
                    )
            );

            return uploadResult.get("secure_url").toString();

        } catch (Exception e) {
            throw new RuntimeException("Upload image failed: " + e.getMessage());
        }
    }

    // XÓA ẢNH TRÊN CLOUD
    public void deleteImage(String imageUrl) {
        try {
            if (imageUrl == null || !imageUrl.contains("cloudinary")) return;

            String publicId = extractPublicId(imageUrl);

            cloudinary.uploader().destroy(publicId, ObjectUtils.asMap(
                    "invalidate", true
            ));

        } catch (Exception e) {
            throw new RuntimeException("Delete Cloudinary image failed: " + e.getMessage());
        }
    }

    // RÚT PUBLIC ID TỪ URL
    private String extractPublicId(String url) {
        // Ví dụ: https://res.cloudinary.com/.../books/abc123.png → books/abc123
        String noParams = url.substring(0, url.lastIndexOf("."));
        return noParams.substring(noParams.indexOf("books/"));
    }
}
