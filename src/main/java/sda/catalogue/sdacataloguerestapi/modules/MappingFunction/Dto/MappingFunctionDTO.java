package sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Dto;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.NotEmpty;


@Data
@Getter
@Setter
public class MappingFunctionDTO {

    @NotEmpty
    @NotNull
    private String mappingFunction;
}
