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

    private String searchTerm;

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    WebAppRequestDto() {
        if(this.getPage() == null) {
            this.page = 1;
        }


        if(this.getSize() == null) {
            this.size = 10;
        }
    }

    private long idWebApp;
    private String applicationName;
    private String pmoNumber;
//    private String role;
    private SapIntegration sapIntegration;
    private String categoryApp;
    private String description;
    private String functionApplication;
    private String address;
    private String businessImpactPriority;
    private String status;
    private String applicationSourceFe;
    private String applicationSourceBe;
    private String ipDatabase;
    private Long sdaHostingEntity;
}
