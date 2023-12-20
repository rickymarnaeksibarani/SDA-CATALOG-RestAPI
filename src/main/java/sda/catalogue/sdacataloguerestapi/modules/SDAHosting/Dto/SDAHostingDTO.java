package sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@Data
@Setter
@Getter
public class SDAHostingDTO {

    @NotNull
    @NotEmpty
    private String sdaHosting;
}
