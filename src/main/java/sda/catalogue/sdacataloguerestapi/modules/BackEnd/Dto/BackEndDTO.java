package sda.catalogue.sdacataloguerestapi.modules.BackEnd.Dto;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.NotEmpty;


@Data
public class BackEndDTO {

    @NotEmpty
    @NotNull
    private String backEnd;
}
