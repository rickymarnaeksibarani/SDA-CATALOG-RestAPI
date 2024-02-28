package sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class MappingFunctionRequestDTO {
    private Integer page;

    private Integer size;

    MappingFunctionRequestDTO() {
        if(this.getPage() == null) {
            this.page = 1;
        }


        if(this.getSize() == null) {
            this.size = 10;
        }
    }
    @JsonProperty( "idMappingFunction")
    private long idMappingFunction;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("mappingFunction")
    private String mappingFunction;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @JsonProperty("dinasList")
    private List<DinasDTO> dinasList = new ArrayList<>();
}
