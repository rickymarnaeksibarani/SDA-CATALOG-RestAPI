package sda.catalogue.sdacataloguerestapi.modules.storage;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {
    @Value("${minio.url}")
    private String endpoint;
    @Value("${minio.port}")
    private Integer port;
    @Value("${minio.access_key}")
    private String accessKey;
    @Value("${minio.secret_key}")
    private String secretKey;
    @Value("${minio.bucket}")
    private String bucket;

    @Override
    public MinioClient initMinioClient() {
        return MinioClient.builder()
                .endpoint(endpoint, port, false)
                .credentials(accessKey, secretKey)
                .build();
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String path) {
        return Paths.get(path);
    }

    @Override
    public Resource loadAsResource(String path) {
        try {
            Path file = load(path);
            Resource urlResource = new UrlResource(file.toUri());

            if (urlResource.exists() || urlResource.isReadable()) return urlResource;
            else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not read file");
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not read file: " + e);
        }
    }

    @Override
    public ObjectWriteResponse storeToS3(String filename, MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try(InputStream inputStream = file.getInputStream()) {
            MinioClient minioClient = initMinioClient();

            // Make 'sda-catalog' bucket if not exist.
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());

            // Make a new bucket called 'sda-catalog'.
            if (!exists) minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());

            return minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(filename.replace("\\", "/").trim())
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

        } catch (MinioException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed upload file to S3: " + e);
        }
    }

    @Override
    public void deleteAllFileS3(List<String> path) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            MinioClient minioClient = initMinioClient();

            LinkedList<DeleteObject> objects;
            objects = path.stream().map(DeleteObject::new).collect(Collectors.toCollection(LinkedList::new));

            Iterable<Result<DeleteError>> results = minioClient.removeObjects(
                    RemoveObjectsArgs.builder().bucket(bucket).objects(objects).build()
            );

            for (Result<DeleteError> result : results) {
                DeleteError error = result.get();
                log.error("Error in deleting object {}; {}", error.objectName(), error.message());
            }
        } catch (MinioException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to delete: " + e);
        }
    }

    @Override
    public void deleteFileS3(String path) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            MinioClient minioClient = initMinioClient();

            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(path)
                    .build());
        } catch (MinioException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to delete file: " + e);
        }
    }

    @Override
    public InputStream getFileFromS3(String path) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            MinioClient minioClient = initMinioClient();
            return minioClient.getObject(
                    GetObjectArgs.builder().bucket(bucket).object(path).build()
            );
        } catch (MinioException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed get file from s3: " + e);
        }
    }

    @Override
    public void deleteAllFilesFromS3(List<String> oldPaths) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        MinioClient minioClient = initMinioClient();

        for (String filePath : oldPaths) {
            try {
                log.info("Deleting file from S3: {}", filePath);

                minioClient.removeObject(RemoveObjectArgs.builder()
                        .bucket(bucket)
                        .object(filePath)
                        .build());

                log.info("Successfully deleted file from S3: {}", filePath);
            } catch (MinioException e) {
                log.error("Failed to delete file from S3: {}", filePath, e);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to delete file: " + e);
            }
        }
    }


}