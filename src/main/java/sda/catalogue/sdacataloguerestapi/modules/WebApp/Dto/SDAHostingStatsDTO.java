package sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SDAHostingStatsDTO {
    private String name;
    private Long total;
}
