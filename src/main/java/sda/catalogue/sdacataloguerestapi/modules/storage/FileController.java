package sda.catalogue.sdacataloguerestapi.modules.storage;

import io.minio.GetObjectResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequestMapping("api/v1/files")
// @CrossOrigin("${spring.frontend}")
public class FileController {
    @Autowired
    private StorageService storageService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<byte[]> getFileFromS3(@RequestParam String path) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        InputStream fileFromS3 = storageService.getFileFromS3(path);
        String filename = StringUtils.getFilename(path);
        byte[] bytes = fileFromS3.readAllBytes();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + filename + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "*")
                .body(bytes);
    }
}
