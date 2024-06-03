package sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import sda.catalogue.sdacataloguerestapi.core.enums.BusinessImpactPriority;
import sda.catalogue.sdacataloguerestapi.core.enums.Role;
import sda.catalogue.sdacataloguerestapi.core.enums.SapIntegration;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MobileAppDto {
    @NotBlank
    private String applicationName;

    @Enumerated
    private Status status;

    private String[] sdaHosting;

    @NotEmpty
    private List<String> mappingFunction;

    private String[] department;

    private List<Role> role;

    @Enumerated
    private BusinessImpactPriority businessImpactPriority;

    @NotEmpty
    private List<String> picDeveloper;

    @NotBlank
    private String description;

    @NotBlank
    private String applicationFunction;

    @JsonIgnore
    private List<MultipartFile> documentation;

    private List<VersioningAppDto> versioningApplication;

    private ApplicationUrlDto applicationUrl;

    @JsonIgnore
    private MultipartFile ipaFile;

    @JsonIgnore
    private MultipartFile androidFile;

    @NotEmpty
    private List<String> sdaFrontEnd;

    @NotEmpty
    private List<String> sdaBackEnd;

    private String webServer;

    @NotNull
    private SapIntegration sapIntegration;

    @NotBlank
    private String applicationSourceFrontEnd;

    @NotBlank
    private String applicationSourceBackEnd;

    @NotBlank
    private String databaseIp;

    @JsonProperty("applicationApiList")
    private List<AppApiListDto> applicationApiList;

    private List<DbListDto> applicationDatabaseList;

    @NotEmpty
    private List<String> picAnalyst;
}
