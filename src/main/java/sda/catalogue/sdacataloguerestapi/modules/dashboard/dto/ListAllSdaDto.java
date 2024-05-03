package sda.catalogue.sdacataloguerestapi.modules.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListAllSdaDto {
    private Long id;
    private String name;
    private List<String> address;
    private List<MappingFunctionEntity> mappingFunction;
    private List<String> role;
    private List<String> department;
    private Status status;
    private String category;
}