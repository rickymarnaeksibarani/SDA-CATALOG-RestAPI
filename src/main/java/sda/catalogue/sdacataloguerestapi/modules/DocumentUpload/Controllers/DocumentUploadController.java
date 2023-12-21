package sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Controllers;

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

@RestController
@RequestMapping("/api/v1/document-upload")
public class DocumentUploadController {
    @Autowired
    DocumentUploadService documentUploadService;

    @PostMapping
    public ResponseEntity<?> createDocumentUpload(
            @RequestParam() List<MultipartFile> documents
    ) {
        try {
            List<DocumentUploadEntity> result = documentUploadService.createDocumentUpload(documents);
            ApiResponse<List<DocumentUploadEntity>> response = new ApiResponse<>(HttpStatus.OK, "Success upload documents!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
