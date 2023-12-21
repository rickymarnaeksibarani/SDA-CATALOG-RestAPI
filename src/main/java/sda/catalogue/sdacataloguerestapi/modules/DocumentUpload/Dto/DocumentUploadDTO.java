package sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Getter
@Setter
public class DocumentUploadDTO {
    @NotEmpty
    @NotNull
    private String path;
}
