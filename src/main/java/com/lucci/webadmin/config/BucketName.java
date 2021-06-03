package com.lucci.webadmin.config;

public enum BucketName {
    IMAGE("lucci");
    private final String bucketName;

    BucketName(String name) {
        bucketName = name;
    }

    public String getBucketName() {
        return bucketName;
    }
}
