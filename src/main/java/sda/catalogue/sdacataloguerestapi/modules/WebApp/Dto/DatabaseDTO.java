package sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseDTO {

    @NotNull
    @NotEmpty
    private String apiName;

    @NotNull
    @NotEmpty
    private String apiAddress;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private Long idTypeDatabase;

}
