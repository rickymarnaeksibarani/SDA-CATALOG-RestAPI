package sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersioningAppDto {
    @NotBlank
    private String version;

    @NotBlank
    private String description;

    @NotBlank
    private String releaseDate;
}
