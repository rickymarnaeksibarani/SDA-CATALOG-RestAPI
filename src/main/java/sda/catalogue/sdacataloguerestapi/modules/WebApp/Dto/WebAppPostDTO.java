package sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;
import sda.catalogue.sdacataloguerestapi.core.enums.BusinessImpactPriority;
import sda.catalogue.sdacataloguerestapi.core.enums.Role;
import sda.catalogue.sdacataloguerestapi.core.enums.SapIntegration;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;
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
    private Long idWebApp;

    @JsonProperty("applicationName")
    private String applicationName;

    @JsonProperty("sapIntegration")
    private SapIntegration sapIntegration;

    @JsonProperty("categoryApp")
    private String categoryApp;

    @NotNull
    @JsonProperty("databaseList")
    private List<DatabaseDTO> databaseList;

    @NotNull
    @JsonProperty("apiApplication")
    private List<ApiDTO> apiApplication;

    @NotNull
    @JsonProperty("versioningApplicationList")
    private List<VersioningApplicationDTO> versioningApplicationList;

    @JsonProperty("description")
    private String description;

    @JsonProperty("functionApplication")
    private String functionApplication;

    @JsonProperty("address")
    private String address;

    @JsonProperty("businessImpactPriority")
    private BusinessImpactPriority businessImpactPriority;

    @Enumerated(EnumType.STRING)
    @JsonProperty("status")
    private Status status;

    @JsonProperty("applicationSourceFe")
    private String applicationSourceFe;

    @JsonProperty("applicationSourceBe")
    private String applicationSourceBe;

    @JsonProperty("ipDatabase")
    private String ipDatabase;

    @JsonProperty("sdaHosting")
    private List<Long> sdaHosting;

    @JsonProperty("picDeveloper")
    private List<Long> picDeveloper;

    @JsonProperty("picAnalyst")
    private List<Long> picAnalyst;

    @JsonProperty("mappingFunction")
    private List<Long> mappingFunction;

    @JsonProperty("frontEnd")
    private List<Long> frontEnd;

    @JsonProperty("backEnd")
    private List<Long> backEnd;

    @JsonProperty("webServer")
    private List<Long> webServer;

    @JsonIgnore
    @JsonProperty("documentUploadList")
    private List<MultipartFile> documentUploadList;
}

