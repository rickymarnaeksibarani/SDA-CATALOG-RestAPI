package sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class SDAHostingRequestDTO {
    private Integer page;
    private Integer size;
    SDAHostingRequestDTO() {
        if(this.getPage() == null) {
            this.page = 1;
        }


        if(this.getSize() == null) {
            this.size = 10;
        }
    }
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
