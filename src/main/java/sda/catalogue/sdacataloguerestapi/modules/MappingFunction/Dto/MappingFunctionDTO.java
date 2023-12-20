package sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
public class MappingFunctionDTO {

    @NotEmpty
    @NotNull
    private String mappingFunction;
}
