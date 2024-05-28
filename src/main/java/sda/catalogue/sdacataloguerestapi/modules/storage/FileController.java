package sda.catalogue.sdacataloguerestapi.modules.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

        String contentType = null;

        if (filename.contains(".pdf")) {
            contentType = "application/pdf";
        } else if (filename.contains(".png")) {
            contentType = "image/png";
        } else if (filename.contains(".apk")) {
            contentType = "application/vnd.android.package-archive";
        } else if (filename.contains(".ipa")) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + filename + "\"")
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(bytes);
    }
}
