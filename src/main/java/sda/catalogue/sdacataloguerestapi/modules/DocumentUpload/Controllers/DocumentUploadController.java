package sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Entities.DocumentUploadEntity;
import sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Services.DocumentUploadService;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/v1/document-upload")
//@CrossOrigin(origins = "${spring.frontend}")
public class DocumentUploadController {
    @Autowired
    DocumentUploadService documentUploadService;

    @PostMapping
    public ResponseEntity<?> createDocumentUpload(
            @RequestParam() List<MultipartFile> documents,
            @RequestParam() Integer webAppId
    ) {
        try {
            List<DocumentUploadEntity> result = documentUploadService.createDocumentUpload(documents, webAppId);
            ApiResponse<List<DocumentUploadEntity>> response = ApiResponse.<List<DocumentUploadEntity>>builder()
                    .status(HttpStatus.OK)
                    .message("Success upload documents")
                    .result(result)
                    .build();

//            ApiResponse<List<DocumentUploadEntity>> response = new ApiResponse<>(HttpStatus.OK, "Success upload documents!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
