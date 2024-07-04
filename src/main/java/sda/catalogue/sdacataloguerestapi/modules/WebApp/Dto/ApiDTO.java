package sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String apiName;

    @NotBlank
    private String ipApi;

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

}
