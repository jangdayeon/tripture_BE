package com.photoChallenger.tripture.global.S3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class S3Service {
    private final AmazonS3 amazonS3;
    private final String bucket;

    public S3Service(AmazonS3 amazonS3, @Value("${cloud.aws.s3.bucket}") String bucket) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
    }

    public String upload(MultipartFile multipartFile, String dir) throws IOException {
        String fileName = multipartFile.getOriginalFilename();

        String uuid = UUID.randomUUID().toString();
        String uniqueFileName = dir + "/" + uuid + "-" + fileName.replace("//s", "-");

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket,
                uniqueFileName,
                multipartFile.getInputStream(),
                getObjectMetadata(multipartFile));

        try {
            amazonS3.putObject(putObjectRequest);
        } catch (AmazonServiceException e) {
            log.error(e.getErrorMessage());
        }

        return uniqueFileName;
    }

    public ObjectMetadata getObjectMetadata(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        return metadata;
    }

    public String delete(String fileName) throws IOException {
        try {
            amazonS3.getObjectMetadata(new GetObjectMetadataRequest(bucket, fileName));
        } catch (AmazonServiceException e) {
            throw new AmazonServiceException("This file does not exist");
        }

        amazonS3.deleteObject(bucket, fileName);
        return "Deleted complete";
    }
}

