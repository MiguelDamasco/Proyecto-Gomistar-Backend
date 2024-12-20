package com.gomistar.proyecto_gomistar.configuration.s3;

import org.springframework.context.annotation.Bean;


import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.regions.Region;

@Configuration
public class S3ClientConfiguration {
    
    @Value("${aws.accessKeyId}")
    private String accesKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Bean
    public S3Client s3Client() {
        
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accesKey, secretKey);
        
        S3Client s3Client = S3Client.builder()
        .region(Region.US_EAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds)) 
                .build();

        return s3Client;
    }
}
