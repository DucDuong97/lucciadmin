package com.lucci.webadmin.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

//    @Value("${amazon.s3.credential.accessKey}")
    private String accessKey = "AKIAU4NWC3CKMDBOVHFY";
//    @Value("${amazon.s3.credential.secretKey}")
    private String secretKey = "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";
//    @Value("${amazon.s3.region}")
    private String region = "ap-southeast-1";

    @Bean
    public AmazonS3 s3() {
        AWSCredentials awsCredentials =
            new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder
            .standard()
            .withRegion(region)
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .build();

    }
}
