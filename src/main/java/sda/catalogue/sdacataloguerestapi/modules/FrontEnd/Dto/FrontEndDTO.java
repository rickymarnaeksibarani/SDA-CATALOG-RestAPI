package sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Dto;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Getter
@Setter
public class FrontEndDTO {

    @NotEmpty
    @NotNull
    private String frontEnd;
}
