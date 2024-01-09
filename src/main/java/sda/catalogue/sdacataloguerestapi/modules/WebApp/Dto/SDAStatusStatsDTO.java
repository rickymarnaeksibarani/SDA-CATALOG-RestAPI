package sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class SDAStatusStatsDTO {
    private int active;
    private int underConstruction;
    private int underReview;
    private int inactive;
}
