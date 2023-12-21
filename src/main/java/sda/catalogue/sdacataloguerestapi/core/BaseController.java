package sda.catalogue.sdacataloguerestapi.core;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@ControllerAdvice
public class BaseController {
    public void isValidDocumentType(MultipartFile file) {
        if (!Objects.equals(file.getContentType(), "application/pdf") || !Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
            file.getContentType();
        }
    }

    public String generateNewFilename(String originalFilename) {
        // Generate a new filename, for example, using UUID
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        return UUID.randomUUID().toString() + extension;
    }
}
