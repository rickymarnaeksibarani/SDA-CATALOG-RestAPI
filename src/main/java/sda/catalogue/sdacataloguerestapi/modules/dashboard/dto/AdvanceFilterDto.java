package sda.catalogue.sdacataloguerestapi.modules.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sda.catalogue.sdacataloguerestapi.core.enums.BusinessImpactPriority;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvanceFilterDto {
    private List<String> mappingFunction;
    private List<String> department;
    private List<String> role;
    private BusinessImpactPriority businessImpactPriority;
    private Status status;
}
