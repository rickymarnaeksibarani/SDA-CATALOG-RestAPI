package sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbListDto {
    @NotBlank
    private String dbName;

    @NotBlank
    private String ipDatabase;

    @NotBlank
    private String type;

    @NotBlank
    private String user;

    @NotBlank
    private String password;
}
