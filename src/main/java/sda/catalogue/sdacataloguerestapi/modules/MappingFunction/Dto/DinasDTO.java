package sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Getter
@Setter
public class DinasDTO {
    @NotNull
    @NotEmpty
    private String dinas;
}
