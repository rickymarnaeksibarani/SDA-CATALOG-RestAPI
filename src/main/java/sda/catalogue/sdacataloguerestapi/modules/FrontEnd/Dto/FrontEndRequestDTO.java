package sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class FrontEndRequestDTO {
    private Integer page;
    private Integer size;
    private String searchTerm;

    FrontEndRequestDTO() {
        if(this.getPage() == null) {
            this.page = 1;
        }


        if(this.getSize() == null) {
            this.size = 10;
        }
    }
    @JsonProperty("idFrontend")
    private long idFrontEnd;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("frontEnd")
    private String frontEnd;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

}
