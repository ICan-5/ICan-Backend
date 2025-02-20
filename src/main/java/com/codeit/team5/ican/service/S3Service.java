package com.codeit.team5.ican.service;

import com.codeit.team5.ican.exception.AmazonS3Exception;
import com.codeit.team5.ican.exception.InvalidImageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    private static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024; // 5MB 제한
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png");

    public void deleteImage(String s3Url) {
        try {
            s3Client.deleteObject(b -> b.bucket(bucketName).key(extractS3Key(s3Url)));
        } catch(Exception e) {
            log.error("S3 이미지 삭제 실패 : {}, {}", s3Url, e.getMessage());
        }
    }

    public String uploadImage(MultipartFile image, String path, String name) {
        validateImage(image);

        try {
            String fileExtension = getFileExtension(image.getOriginalFilename());
            String fileName = String.format("%s/%s.%s", path, name, fileExtension);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(image.getContentType())
                    .build();

            PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(image.getBytes()));

            if (response.sdkHttpResponse().isSuccessful()) {
                return getFileUrl(fileName);
            } else {
                throw new AmazonS3Exception(response.sdkHttpResponse().statusText().orElse("Unknown error"));
            }
        } catch (SdkException | AmazonS3Exception e) {
            throw new AmazonS3Exception("S3 업로드 실패: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("서버 에러 : " + e.getMessage());
        }
    }

    private String extractS3Key(String s3Url) {
        try {
            URI uri = new URI(s3Url);
            return uri.getPath().substring(1); // 첫 번째 슬래시(/) 제거
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("잘못된 S3 URL입니다.");
        }
    }

    private void validateImage(MultipartFile image) {
        if (image.getSize() > MAX_IMAGE_SIZE) {
            throw new InvalidImageException("파일 크기가 5MB를 초과할 수 없습니다.");
        }

        // 파일 확장자 검사
        String originalName = image.getOriginalFilename();
        if (originalName == null || !ALLOWED_EXTENSIONS.contains(getFileExtension(originalName))) {
            throw new InvalidImageException("허용되지 않은 파일 형식입니다. (JPG, PNG만 가능)");
        }

        // MIME 타입 검사
        String contentType = image.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType)) {
            throw new InvalidImageException("허용되지 않은 파일 형식입니다. (JPG, PNG만 가능)");
        }
    }

    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new InvalidImageException("파일 확장자가 없습니다.");
        }
        return filename.substring(lastDotIndex + 1).toLowerCase();
    }


    private String getFileUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileName);
    }

}
