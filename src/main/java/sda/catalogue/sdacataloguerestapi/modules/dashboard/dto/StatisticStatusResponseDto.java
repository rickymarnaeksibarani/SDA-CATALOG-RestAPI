package sda.catalogue.sdacataloguerestapi.modules.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticStatusResponseDto {
    private Integer active;
    private Integer inActive;
    private Integer underReview;
    private Integer underConstruction;
}
