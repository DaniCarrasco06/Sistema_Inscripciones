package com.duoc.SistemaDeInscripcion.repository;

// Se cambia todos los imports de com.amazonaws (SDK v1) reemplazados por software.amazon.awssdk (SDK v2)
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.duoc.SistemaDeInscripcion.model.Asset; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class S3RepositoryImpl implements S3Repository {

       private S3Client s3Client; // Se cambia AmazonS3 a S3Client (SDK v2)

    @Autowired
    public S3RepositoryImpl(S3Client s3Client) { // Se cambia AmazonS3 a S3Client (SDK v2)
        this.s3Client = s3Client;
    }

    private static final Logger log = LoggerFactory.getLogger(S3RepositoryImpl.class);

    @Override
    public List<Asset> listObjectsInBucket(String bucket) {
        // listObjectsV2(bucket) a listObjectsV2(request) con builder (SDK v2)
        List<Asset> items = s3Client.listObjectsV2(
                ListObjectsV2Request.builder().bucket(bucket).build())
                .contents()
                .stream()
                .parallel()
                .map(s3Object -> s3Object.key()) // S3ObjectSummary::getKey a s3Object.key() (SDK v2)
                .map(key -> mapS3ToObject(bucket, key))
                .collect(Collectors.toList());

        log.info("Found " + items.size() + " objects in the bucket " + bucket);
        return items;
    }

    private Asset mapS3ToObject(String bucket, String key) {
        // getObjectMetadata().getUserMetaDataOf() a headObject().metadata().get() (SDK v2)
        String name = s3Client.headObject(
                HeadObjectRequest.builder().bucket(bucket).key(key).build())
                .metadata().get("name");

        // getUrl() no existe en SDK v2, se construye la URL manualmente
        URL url;
        try {
            url = new URL("https://" + bucket + ".s3.amazonaws.com/" + key);
        } catch (Exception e) {
            url = null;
        }

        return Asset.builder()
                .name(name)
                .key(key)
                .url(url)
                .build();
    }

    @Override
    public ResponseInputStream<GetObjectResponse> getObject(String bucketName, String fileName) throws IOException {
        // doesBucketExistV2() a headBucket() con try/catch (SDK v2)
        try {
            s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
        } catch (NoSuchBucketException e) {
            log.error("No Bucket Found");
            return null;
        }

        // getObject(bucket, key) a getObject(request) con builder (SDK v2)
        return s3Client.getObject(
                GetObjectRequest.builder().bucket(bucketName).key(fileName).build());
    }

    @Override
    public byte[] downloadFile(String bucketName, String fileName) throws IOException {
        // S3ObjectInputStream a ResponseInputStream<GetObjectResponse> (SDK v2)
        ResponseInputStream<GetObjectResponse> inputStream = s3Client.getObject(
                GetObjectRequest.builder().bucket(bucketName).key(fileName).build());
        try {
            // IOUtils.toByteArray() de SDK v1 a readAllBytes() de Java nativo (SDK v2)
            return inputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void moveObject(String bucketName, String fileKey, String destinationFileKey) {
        // Se cambia new CopyObjectRequest(bucket, key, bucket, dest) a builder (SDK v2)
        s3Client.copyObject(CopyObjectRequest.builder()
                .sourceBucket(bucketName)
                .sourceKey(fileKey)
                .destinationBucket(bucketName)
                .destinationKey(destinationFileKey)
                .build());
        deleteObject(bucketName, fileKey);
    }

    @Override
    public void deleteObject(String bucketName, String fileKey) {
        // Se cambia deleteObject(bucket, key) a deleteObject(request) con builder (SDK v2)
        s3Client.deleteObject(
                DeleteObjectRequest.builder().bucket(bucketName).key(fileKey).build());
    }

    @Override
    public String uploadFile(String bucketName, String fileName, File fileObj) {
        // Se cambia new PutObjectRequest(bucket, key, file) a builder + RequestBody.fromFile() (SDK v2)
        s3Client.putObject(
                PutObjectRequest.builder().bucket(bucketName).key(fileName).build(),
                RequestBody.fromFile(fileObj));
        fileObj.delete();
        return "File uploaded : " + fileName;
    }
}
