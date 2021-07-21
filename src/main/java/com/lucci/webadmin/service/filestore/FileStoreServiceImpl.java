package com.lucci.webadmin.service.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.lucci.webadmin.service.FileStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
public class FileStoreServiceImpl implements FileStoreService {

    @Value("${amazon.s3.lucci.erp.bucketName}")
    private String erpBucketName;

    private final Logger log = LoggerFactory.getLogger(FileStoreServiceImpl.class);
    private final AmazonS3 amazonS3;

    public FileStoreServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public void upload(String key, Map<String, String> metaData, InputStream is) {
        ObjectMetadata omd = new ObjectMetadata();
        metaData.forEach(omd::addUserMetadata);
        try {
            PutObjectRequest por = new PutObjectRequest(erpBucketName, key, is, omd);
            amazonS3.putObject(por);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }

    @Override
    public void delete(String key) {
        log.debug("Request to delete file: {}/{} on S3", erpBucketName, key);
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(erpBucketName, key);
        amazonS3.deleteObject(deleteObjectRequest);
    }

    @Override
    public byte[] download(String key) {
        try {
            S3Object object = amazonS3.getObject(erpBucketName, key);
            S3ObjectInputStream objectContent = object.getObjectContent();
            return IOUtils.toByteArray(objectContent);
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download the file", e);
        }
    }

    @Override
    public void copy(String sourceKey, String destKey) {
        log.debug("Request to copy file: {}/{} to {}/{} on S3", erpBucketName, sourceKey, erpBucketName, destKey);
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(erpBucketName, sourceKey, erpBucketName, destKey);
        amazonS3.copyObject(copyObjectRequest);
    }

    @Override
    public String getBucketName() {
        return erpBucketName;
    }
}
