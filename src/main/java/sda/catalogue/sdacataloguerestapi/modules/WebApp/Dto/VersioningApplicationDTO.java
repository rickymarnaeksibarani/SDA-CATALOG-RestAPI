package sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersioningApplicationDTO {
    @NotNull
    @NotEmpty
    private String version;

    @NotNull
    @NotEmpty
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotNull
    @NotEmpty
    private Date releaseDate;

//    @NotNull
//    @NotEmpty
//    private WebAppEntity webAppEntity;
}
