package sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppApiListDto {
    @NotBlank
    private String apiName;

    @NotBlank
    private String ipApi;

    @NotBlank
    private String user;

    @NotBlank
    private String password;
}
