package sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import sda.catalogue.sdacataloguerestapi.core.enums.MasterDataStatus;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.DatabaseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class TypeDatabaseDTO {

    @JsonProperty("idTypeDatabase")
    private long idTypeDatabase;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("typeDatabase")
    private String typeDatabase;

    @NotNull
    private MasterDataStatus dbStatus;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @JsonProperty("typeDatabaseEntity")
    private List<DatabaseEntity> databaseEntities;
}
