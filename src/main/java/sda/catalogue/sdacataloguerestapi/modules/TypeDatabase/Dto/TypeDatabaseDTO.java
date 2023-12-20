package sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Dto;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Getter
@Setter
public class TypeDatabaseDTO {

    @NotEmpty
    @NotNull
    private String typeDatabase;
}
