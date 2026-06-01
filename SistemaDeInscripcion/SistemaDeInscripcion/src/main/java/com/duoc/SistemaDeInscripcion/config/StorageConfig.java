package com.duoc.sistemadeinscripcion.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Se reemplazaron los imports de com.amazonaws (SDK v1) por software.amazon.awssdk (SDK v2)
// porque spring-cloud-aws-starter-s3 usa SDK v2
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class StorageConfig {

        @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String accessSecret;

    @Value("${cloud.aws.credentials.session-token}")
    private String sessionToken;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public S3Client s3Client() { // Se cambia AmazonS3 a S3Client (SDK v2)

        // Se cambia: BasicSessionCredentials por AwsSessionCredentials (SDK v2)
        AwsSessionCredentials credentials = AwsSessionCredentials.create(accessKey, accessSecret, sessionToken);

        // Se cambia: AmazonS3ClientBuilder.standard() → S3Client.builder() (SDK v2)
        // Se cambia: withCredentials() → credentialsProvider() (SDK v2)
        // Se cambia: withRegion() → region(Region.of()) (SDK v2, requiere objeto Region en vez de String)
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of(region))
                .build();
    }

}
