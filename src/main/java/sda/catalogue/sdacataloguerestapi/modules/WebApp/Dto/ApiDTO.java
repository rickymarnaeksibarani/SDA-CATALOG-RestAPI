package sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiDTO {
    @NotNull
    @NotEmpty
    private String apiName;

    @NotNull
    @NotEmpty
    private String ipApi;

    @NotNull
    @NotEmpty String userName;

    @NotNull
    @NotEmpty
    private String password;
}
