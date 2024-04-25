package sda.catalogue.sdacataloguerestapi.modules.BackEnd.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BackEndRequestDTO {
    private Integer page;

    private Integer size;
    private String searchTerm;

    BackEndRequestDTO() {
        if(this.getPage() == null) {
            this.page = 1;
        }


        if(this.getSize() == null) {
            this.size = 10;
        }
    }
    @JsonProperty("idBackEnd")
    private long idBackEnd;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("backEnd")
    private String backEnd;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
