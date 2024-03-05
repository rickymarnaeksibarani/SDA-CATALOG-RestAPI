package sda.catalogue.sdacataloguerestapi.modules.pmonumber.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PmoNumberDTO {
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
