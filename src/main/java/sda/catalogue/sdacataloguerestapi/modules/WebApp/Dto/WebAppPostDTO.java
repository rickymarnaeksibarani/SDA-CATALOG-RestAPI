package sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
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

//    @JsonProperty("role")
//    private Role role;

    @JsonProperty("categoryApp")
    private String categoryApp;

    @JsonProperty("description")
    private String description;

    @JsonProperty("functionApplication")
    private String functionApplication;

    @JsonProperty("address")
    private String address;

    @JsonProperty("businessImpactPriority")
    private BusinessImpactPriority businessImpactPriority;

    @Enumerated
    private Status status;

    @JsonProperty("applicationSourceFe")
    private String applicationSourceFe;

    @JsonProperty("applicationSourceBe")
    private String applicationSourceBe;

    @JsonProperty("ipDatabase")
    private String ipDatabase;

    @JsonProperty("sdaHosting")
    private List<Long> sdaHosting;

    @JsonIgnore
    private List<String> picDeveloper;

    @JsonIgnore
    private List<String> picAnalyst;

    @JsonIgnore
    private List<String> mappingFunction;

    @JsonIgnore
    private List<String> frontEnd;

    @JsonIgnore
    private List<String> backEnd;

    @JsonIgnore
    private List<String> webServer;

    @JsonProperty("documentUploadList")
    private List<MultipartFile> documentUploadList;


}
