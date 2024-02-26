package sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class DinasDTO {
    @JsonProperty("idDinas")
    private long idDinas;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("dinas")
    private String dinas;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @JsonProperty("mappingFunctionEntity")
    private MappingFunctionEntity mappingFunctionEntity;


}
