package sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class SDAHostingDTO {

    @JsonProperty("idSDAHosting")
    private long idSDAHosting;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("sdaHosting")
    private String sdaHosting;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @JsonProperty("sdaHostingEntity")
    private List<WebAppEntity> sdaHostingEntities;
}