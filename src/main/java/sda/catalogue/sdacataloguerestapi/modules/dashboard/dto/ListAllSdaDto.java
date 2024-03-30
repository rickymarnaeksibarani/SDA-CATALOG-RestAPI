package sda.catalogue.sdacataloguerestapi.modules.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListAllSdaDto {
    private Long id;
    private String name;
    private List<String> address;
    private List<String> mappingFunction;
    private List<String> role;
    private List<String> department;
    private Status status;
}