package sda.catalogue.sdacataloguerestapi.modules.WebServer.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.WebAppRequestDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class WebServerRequestDTO {
    private Integer page;
    private Integer size;
    WebServerRequestDTO(){
        if (this.getPage() == null){
            this.page= 1;
        }
        if (this.getSize() == null){
            this.size= 10;
        }
    }
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
