package sda.catalogue.sdacataloguerestapi.modules.pmonumber.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PmoNumberRequestDTO {
    private Integer page;
    private Integer size;
    PmoNumberRequestDTO() {
        if(this.getPage() == null) {
            this.page = 1;
        }


        if(this.getSize() == null) {
            this.size = 10;
        }
    }
    @JsonProperty("idPMO")
    private long idPMO;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("pmoNumber")
    private String pmoNumber;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
