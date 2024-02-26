package sda.catalogue.sdacataloguerestapi.modules.BackEnd.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.NotEmpty;

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

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
