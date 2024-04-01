package sda.catalogue.sdacataloguerestapi.modules.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class PagingRequest {
    @JsonIgnore
    private Integer page;

    @JsonIgnore
    private Integer size;

    private String orderBy;
    private String order; // ASC | DESC
    private String search;
    private List<String> filterByTech;

    // For advance filter purpose
    private List<String> mappingFunction;
    private List<String> department;
    private List<String> role;
    private BusinessImpactPriority businessImpactPriority;

    @Enumerated(EnumType.STRING)
    private List<Status> status;
}
