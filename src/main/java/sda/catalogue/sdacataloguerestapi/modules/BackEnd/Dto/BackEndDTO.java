package sda.catalogue.sdacataloguerestapi.modules.BackEnd.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import sda.catalogue.sdacataloguerestapi.core.enums.MasterDataStatus;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
public class BackEndDTO {
    @JsonProperty("idBackEnd")
    private long idBackEnd;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("backEnd")
    private String backEnd;

    @NotNull
    private MasterDataStatus beStatus;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
