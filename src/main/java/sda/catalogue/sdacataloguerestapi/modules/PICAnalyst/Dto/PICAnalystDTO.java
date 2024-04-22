package sda.catalogue.sdacataloguerestapi.modules.PICAnalyst.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PICAnalystDTO {
    @JsonProperty("idPicAnalyst")
    private long idPicAnalyst;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("personalNumber")
    private String personalNumber;

    @JsonProperty("personalName")
    private String personalName;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
