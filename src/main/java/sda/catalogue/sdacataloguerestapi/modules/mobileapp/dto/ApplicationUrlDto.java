package sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUrlDto {
    @NotBlank
    private String appstoreUrl;

    @NotBlank
    private String playstoreUrl;
}
