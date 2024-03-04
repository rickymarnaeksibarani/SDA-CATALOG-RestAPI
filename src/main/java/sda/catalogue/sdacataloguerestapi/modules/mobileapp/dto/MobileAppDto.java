package sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto;

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

    @NotBlank
    private String pmoNumber;

    @Enumerated
    private Status status;

    private List<String> sdaHosting;

    @NotEmpty
    private String[] mappingFunction;

    @NotEmpty
    private String[] department;

    @NotEmpty
    private List<Role> role;

    @Enumerated
    private BusinessImpactPriority businessImpactPriority;

    @NotEmpty
    private String[] picDeveloper;

    @NotBlank
    private String description;

    @NotBlank
    private String applicationFunction;

    private List<MultipartFile> documentation;

    private List<VersioningAppDto> versioningApplication;

    private ApplicationUrlDto applicationUrl;

    private MultipartFile ipaFile;
    private MultipartFile androidFile;

    @NotEmpty
    private String[] sdaFrontEnd;

    @NotEmpty
    private String[] sdaBackEnd;

    @NotBlank
    private String webServer;

    @NotNull
    private SapIntegration sapIntegration;

    @NotBlank
    private String applicationSourceFrontEnd;

    @NotBlank
    private String applicationSourceBackEnd;

    @NotBlank
    private String databaseIp;

    private List<AppApiListDto> applicationApiList;

    private List<DbListDto> applicationDatabaseList;
}
