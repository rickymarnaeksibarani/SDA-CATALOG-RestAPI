package sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.NotEmpty;
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

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @JsonProperty("typeDatabaseEntity")
    private List<DatabaseEntity> databaseEntities;
}
