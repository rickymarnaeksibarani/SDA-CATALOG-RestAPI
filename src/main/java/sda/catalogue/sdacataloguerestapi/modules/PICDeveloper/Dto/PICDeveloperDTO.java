package sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import sda.catalogue.sdacataloguerestapi.core.enums.MasterDataStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
public class PICDeveloperDTO {
    @JsonProperty("idPicDeveloper")
    private long idPicDeveloper;

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
 