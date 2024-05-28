package sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationFileDto {
    String filename;
    String path;
    String mimeType;
    Long size;
}
