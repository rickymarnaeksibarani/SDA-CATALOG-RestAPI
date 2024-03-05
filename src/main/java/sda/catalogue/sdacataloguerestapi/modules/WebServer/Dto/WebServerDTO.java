package sda.catalogue.sdacataloguerestapi.modules.WebServer.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
public class WebServerDTO {
    @JsonProperty("idWebServer")
    private long idWebServer;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("webServer")
    private String webServer;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}