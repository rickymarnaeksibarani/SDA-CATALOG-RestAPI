package sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WebAppPostDTO {

    @NotBlank(message = "application name is required!")
    private String applicationName;

    @NotBlank(message = "description is required!")
    private String description;

    @NotBlank(message = "function application is required!")
    private String functionApplication;

    @NotBlank(message = "address is required!")
    private String address;

    @NotBlank(message = "dinas is required!")
    private String dinas;

    @NotBlank(message = "mapping function is required!")
    private String mappingFunction;

    @NotBlank(message = "business impact priority is required!")
    private String businessImpactPriority;

    @NotBlank(message = "status is required!")
    private String status;

    @NotBlank(message = "sda cloud is required!")
    private String sdaCloud;
}
