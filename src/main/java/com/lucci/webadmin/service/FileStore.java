package com.lucci.webadmin.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class FileStore {

    private final Logger log = LoggerFactory.getLogger(FileStore.class);
    private final AmazonS3 amazonS3;

    public FileStore(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void upload(String path,
                       String fileName,
                       Optional<Map<String, String>> optionalMetaData,
                       InputStream inputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetaData.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(objectMetadata::addUserMetadata);
            }
        });
        try {
            amazonS3.putObject(path, fileName, inputStream, objectMetadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }

    public void delete(String bucketName, String fileName) {
        log.debug("Request to delete file: {}/{} on S3",bucketName, fileName);
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, fileName);
        amazonS3.deleteObject(deleteObjectRequest);
    }

//    public byte[] download(String path, String key) {
//        try {
//            S3Object object = amazonS3.getObject(path, key);
//            S3ObjectInputStream objectContent = object.getObjectContent();
//            return IOUtils.toByteArray(objectContent);
//        } catch (AmazonServiceException | IOException e) {
//            throw new IllegalStateException("Failed to download the file", e);
//        }
//    }

}
