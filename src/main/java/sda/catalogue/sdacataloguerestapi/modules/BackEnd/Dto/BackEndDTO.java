package sda.catalogue.sdacataloguerestapi.modules.BackEnd.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
public class BackEndDTO {

    @NotEmpty
    @NotNull
    private String backEnd;
}
