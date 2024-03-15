package sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;
import sda.catalogue.sdacataloguerestapi.core.enums.SapIntegration;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Entities.FeedbackEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.DatabaseEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.VersioningApplicationEntity;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebAppPostDTO {

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


    private String linkIOS;


    private String linkAndroid;


    private MultipartFile fileManifest;


    private MultipartFile fileIpa;


    private MultipartFile fileAndroid;

    @JsonProperty("applicationSourceFe")
    private String applicationSourceFe;

    @JsonProperty("applicationSourceBe")
    private String applicationSourceBe;

    @JsonProperty("ipDatabase")
    private String ipDatabase;

    @JsonProperty("sdaHosting")
    private List<Long> sdaHosting;

    @JsonProperty("documentUploadList")
    private List<MultipartFile> documentUploadList;


}
