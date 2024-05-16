package com.edu.tool.service;

import com.edu.tool.exception.ImageUploadException;
import com.edu.tool.model.ToolImage;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ImageService {
    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    @Value("${minio.host-image}")
    private String minioHost;

    public String upload(
        final MultipartFile image
    ) {
        try {
            createBucket();
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed: "
                + e.getMessage());
        }
        MultipartFile file = image;
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new ImageUploadException("Image must have name.");
        }
        String fileName = generateFileName(file);
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed: "
                + e.getMessage());
        }
        saveImage(inputStream, fileName);
        return minioHost + "/" + bucket + "/"+ fileName;
    }

    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
            .bucket(bucket)
            .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                .bucket(bucket)
                .build());
        }
    }

    private String generateFileName(
        final MultipartFile file
    ) {
        String extension = getExtension(file);
        return UUID.randomUUID() + "." + extension;
    }

    private String getExtension(
        final MultipartFile file
    ) {
        return file.getOriginalFilename()
            .substring(file.getOriginalFilename()
                .lastIndexOf(".") + 1);
    }

    @SneakyThrows
    private void saveImage(
        final InputStream inputStream,
        final String fileName
    ) {
        minioClient.putObject(PutObjectArgs.builder()
            .stream(inputStream, inputStream.available(), -1)
            .bucket(bucket)
            .object(fileName)
            .build());
    }
}
