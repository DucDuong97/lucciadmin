package com.lucci.webadmin.service;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface FileStoreService {

    void upload(String bucket, String key, Map<String, String> metaData, InputStream is);
    byte[] download(String bucket, String key);
    void copy(String bucket, String sourceKey, String destKey);
    void delete(String bucket, String key);

    default void move(String bucket, String sourceKey, String destKey) {
        copy(bucket, sourceKey, destKey);
        delete(bucket, sourceKey);
    }
}
