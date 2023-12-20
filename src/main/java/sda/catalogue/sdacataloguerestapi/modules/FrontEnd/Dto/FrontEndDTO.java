package sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
public class FrontEndDTO {

    @NotEmpty
    @NotNull
    private String frontEnd;
}
