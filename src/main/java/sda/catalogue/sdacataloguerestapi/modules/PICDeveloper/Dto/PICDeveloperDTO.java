package sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.NotEmpty;


@Data
public class PICDeveloperDTO {
    @NotEmpty
    @NotNull
    private String personalNumber;

    @NotEmpty
    @NotNull
    private String personalName;
}
 