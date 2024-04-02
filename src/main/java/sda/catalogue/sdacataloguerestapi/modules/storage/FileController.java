package sda.catalogue.sdacataloguerestapi.modules.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/files")
public class FileController {
    @Autowired
    private StorageService storageService;

    @ResponseBody
    @GetMapping
    public ResponseEntity<Resource> serveFile(@RequestParam String path) {
        Resource file = storageService.loadAsResource(path);
        if (file == null) return ResponseEntity.notFound().build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
