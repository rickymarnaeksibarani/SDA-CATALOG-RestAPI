package sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Dto;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentUploadDTO {
    String path;
    String filename;
    String mimeType;
    Long size;

}
