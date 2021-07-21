package com.lucci.webadmin.service;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface FileStoreService {

    void upload(String key, Map<String, String> metaData, InputStream is);
    byte[] download(String key);
    void copy(String sourceKey, String destKey);
    void delete(String key);
    String getBucketName();

    default void move(String sourceKey, String destKey) {
        copy(sourceKey, destKey);
        delete(sourceKey);
    }
}
