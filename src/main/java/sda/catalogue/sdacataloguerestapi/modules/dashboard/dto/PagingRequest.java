package sda.catalogue.sdacataloguerestapi.modules.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String filterByTech;
    private AdvanceFilterDto advanceFilter;
}
