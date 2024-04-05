package sda.catalogue.sdacataloguerestapi.modules.storage;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
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
    @Override
    public MinioClient initMinioClient() {
        return MinioClient.builder()
                .endpoint("http://172.16.41.73", 9000, false)
                .credentials("NzuPCWpGUqoOPHgbCQJL", "YdoevporrUm3ddrytfBTXqWFT3JXVlR3DyzzwaZR")
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
    public void deleteAllFileS3(List<String> path) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            MinioClient minioClient = initMinioClient();

            LinkedList<DeleteObject> objects;
            objects = path.stream().map(DeleteObject::new).collect(Collectors.toCollection(LinkedList::new));

            Iterable<Result<DeleteError>> results = minioClient.removeObjects(
                    RemoveObjectsArgs.builder().bucket("sda-catalog").objects(objects).build()
            );

            for (Result<DeleteError> result : results) {
                DeleteError error = result.get();
                log.error("Error in deleting object " + error.objectName() + "; " + error.message());
            }
        } catch (MinioException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to delete: " + e);
        }
    }

    @Override
    public ObjectWriteResponse storeToS3(String filename, MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try(InputStream fileStream = file.getInputStream()) {
            MinioClient minioClient = initMinioClient();

            // Make 'sda-catalog' bucket if not exist.
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket("sda-catalog").build());

            // Make a new bucket called 'sda-catalog'.
            if (!exists) minioClient.makeBucket(MakeBucketArgs.builder().bucket("sda-catalog").build());

            return minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("sda-catalog")
                            .object(filename.replace("\\", "/").trim())
                            .stream(fileStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

        } catch (MinioException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed upload file to S3: " + e);
        }
    }

    @Override
    public void deleteFileS3(String path) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            MinioClient minioClient = initMinioClient();

            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket("sda-catalog")
                    .object(path)
                    .build());
        } catch (MinioException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to delete file: " + e);
        }
    }
}
