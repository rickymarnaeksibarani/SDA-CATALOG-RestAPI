package sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.DatabaseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class TypeDatabaseRequestDTO {
    private Integer page;

    private Integer size;

    TypeDatabaseRequestDTO() {
        if(this.getPage() == null) {
            this.page = 1;
        }


        if(this.getSize() == null) {
            this.size = 10;
        }
    }
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

}
