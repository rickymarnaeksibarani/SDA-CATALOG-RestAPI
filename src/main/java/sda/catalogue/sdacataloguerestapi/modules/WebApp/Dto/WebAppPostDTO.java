package sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto;

import lombok.*;
import sda.catalogue.sdacataloguerestapi.core.TangerangValidation.TangerangAnnotation.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebAppPostDTO {

//    @TGRNotEmpty
//    @TGRString
    private String applicationName;

//    @TGRNotEmpty
    private String description;

//    @TGRString
    private String functionApplication;

//    @TGRNotEmpty
    private String address;

//    @TGRNotEmpty
    private String dinas;

//    @TGRNotEmpty
    private String mappingFunction;

//    @TGRNotEmpty
    private String businessImpactPriority;

//    @TGRNotEmpty
//    @TGREmail
    private String status;

//    @TGRNotEmpty
    private String sdaCloud;
}
