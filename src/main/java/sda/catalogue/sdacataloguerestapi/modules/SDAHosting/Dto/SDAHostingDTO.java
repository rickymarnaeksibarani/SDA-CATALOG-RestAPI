package sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class SDAHostingDTO {

    @NotNull
    @NotEmpty
    private String sdaHosting;
}
