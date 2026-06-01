package com.duoc.sistemadeinscripcion.repository;

// S3ObjectInputStream de SDK v1 cambia a ResponseInputStream<GetObjectResponse> de SDK v2
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import com.duoc.sistemadeinscripcion.model.Asset; // CAMBIO: package actualizado al proyecto
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface S3Repository {

    List<Asset> listObjectsInBucket(String bucket);

    // S3ObjectInputStream cambia a ResponseInputStream<GetObjectResponse> (SDK v2)
    ResponseInputStream<GetObjectResponse> getObject(String bucketName, String fileName) throws IOException;

    byte[] downloadFile(String bucketName, String fileName) throws IOException;

    void moveObject(String bucketName, String fileKey, String destinationFileKey);

    void deleteObject(String bucketName, String fileKey);

    String uploadFile(String bucketName, String fileName, File fileObj);
}
