package sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sda.catalogue.sdacataloguerestapi.core.BaseController;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Entities.DocumentUploadEntity;
import sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Repositories.DocumentUploadRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DocumentUploadService extends BaseController {
    @Autowired
    private DocumentUploadRepository documentUploadRepository;

    private static final String UPLOAD_DIR = "src/main/resources/uploads/document";

    @Transactional
    public List<DocumentUploadEntity> createDocumentUpload(List<MultipartFile> documents) {

        List<DocumentUploadEntity> documentList = new ArrayList<>();
        try {
            for (MultipartFile dataDocument : documents) {
                super.isValidDocumentType(dataDocument);
                String newFilename = super.generateNewFilename(Objects.requireNonNull(dataDocument.getOriginalFilename()));
                Path uploadPath = Paths.get(UPLOAD_DIR);
                Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(newFilename);
                Files.copy(dataDocument.getInputStream(), filePath);

                DocumentUploadEntity documentItem = new DocumentUploadEntity();
                documentItem.setPath(uploadPath + "\\" + newFilename);

                documentList.add(documentItem);
            }
        } catch (IOException e) {
            throw new CustomRequestException(e.toString(), HttpStatus.BAD_REQUEST);
        }
        return documentUploadRepository.saveAll(documentList);
    }
}
