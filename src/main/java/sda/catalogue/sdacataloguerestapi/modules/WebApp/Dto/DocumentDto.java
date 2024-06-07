package sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {
    String filename;
    String path;
    String mimeType;
    Long size;
}
