package sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.DinasEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
public class MappingFunctionDTO {

    @JsonProperty("idMappingFunction")
    private long idMappingFunction;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("mappingFunction")
    private String mappingFunction;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @JsonProperty( "dinasEntityList")
    private List<DinasEntity> dinasEntityList;

    @Override
    public String toString() {
        return "MappingFunctionDTO{" +
                "idMappingFunction=" + idMappingFunction +
                ", uuid=" + uuid +
                ", mappingFunction='" + mappingFunction + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", dinasEntityList=" + dinasEntityList +
                '}';
    }
}
