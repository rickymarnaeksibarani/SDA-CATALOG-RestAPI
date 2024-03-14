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
    private String dbName;

    @NotNull
    @NotEmpty
    private String dbAddress;

    @NotNull
    @NotEmpty String dbUserName;

    @NotNull
    @NotEmpty
    private String dbPassword;

    @NotNull
    @NotEmpty
    private Long idTypeDatabase;


}
