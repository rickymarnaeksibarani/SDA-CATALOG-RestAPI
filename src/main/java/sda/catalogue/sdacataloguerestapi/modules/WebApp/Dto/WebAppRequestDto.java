package sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import sda.catalogue.sdacataloguerestapi.core.enums.SapIntegration;

import java.util.List;

@Data
public class WebAppRequestDto {

    private Integer page;

    private Integer size;

    WebAppRequestDto() {
        if(this.getPage() == null) {
            this.page = 1;
        }


        if(this.getSize() == null) {
            this.size = 10;
        }
    }


    @JsonProperty("idWebApp")
    private long idWebApp;


    @JsonProperty("applicationName")
    private String applicationName;

    @JsonProperty("pmoNumber")
    private String pmoNumber;

    @JsonProperty("sapIntegration")
    private SapIntegration sapIntegration;

    @JsonProperty("categoryApp")
    private String categoryApp;

    @JsonProperty("description")
    private String description;

    @JsonProperty("functionApplication")
    private String functionApplication;

    @JsonProperty("address")
    private String address;

    @JsonProperty("businessImpactPriority")
    private String businessImpactPriority;

    @JsonProperty("status")
    private String status;

    @JsonProperty("linkIOS")
    private String linkIOS;

    @JsonProperty("linkAndroid")
    private String linkAndroid;

    @JsonProperty("applicationSourceFe")
    private String applicationSourceFe;

    @JsonProperty("applicationSourceBe")
    private String applicationSourceBe;

    @JsonProperty("ipDatabase")
    private String ipDatabase;

    @JsonProperty("sdaHostingEntity")
    private Long sdaHostingEntity;
}
