package sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
public class PICDeveloperDTO {

    @NotEmpty
    @NotNull
    private String personalNumber;

    @NotEmpty
    @NotNull
    private String personalName;
}
