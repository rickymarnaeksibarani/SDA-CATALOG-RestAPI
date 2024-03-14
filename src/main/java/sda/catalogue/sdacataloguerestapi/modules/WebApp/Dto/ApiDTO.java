package sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiDTO {

    @NotNull
    @NotEmpty
    @JsonProperty("apiName")
    private String apiName;

    @NotNull
    @NotEmpty
    @JsonProperty("ipApi")
    private String ipApi;

    @NotNull
    @NotEmpty
    @JsonProperty("userName")
    private String userName;

    @NotNull
    @NotEmpty
    @JsonProperty("password")
    private String password;

    @NotNull
    @NotEmpty
    private WebAppEntity webAppEntity;
}
