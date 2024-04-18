package sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import sda.catalogue.sdacataloguerestapi.core.enums.MasterDataStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class FrontEndDTO {
    @JsonProperty("idFrontend")
    private long idFrontEnd;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("frontEnd")
    private String frontEnd;

    @NotNull
    private MasterDataStatus feStatus;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
