package sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import sda.catalogue.sdacataloguerestapi.core.enums.MasterDataStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class FrontEndRequestDTO {
    private Integer page;
    private Integer size;
    private String searchTerm;
    private List<MasterDataStatus> status;

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
