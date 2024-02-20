package sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Dto;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;


@Data
public class MappingFunctionDTO {

    @NotEmpty
    @NotNull
    private String mappingFunction;

    @NotNull
    @NotEmpty
    private List<DinasDTO> dinasList;
}
