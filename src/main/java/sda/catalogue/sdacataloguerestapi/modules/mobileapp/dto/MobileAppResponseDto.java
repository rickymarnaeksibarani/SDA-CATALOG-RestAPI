package sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import sda.catalogue.sdacataloguerestapi.core.enums.BusinessImpactPriority;
import sda.catalogue.sdacataloguerestapi.core.enums.Role;
import sda.catalogue.sdacataloguerestapi.core.enums.SapIntegration;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MobileAppResponseDto {
    private String applicationName;

    
    private String pmoNumber;

    
    private Status status;

    private List<String> sdaHosting;

    
    private String[] mappingFunction;

    
    private String[] department;

    
    private List<Role> role;

    
    private BusinessImpactPriority businessImpactPriority;

    
    private String[] picDeveloper;

    
    private String description;

    
    private String applicationFunction;

    private List<MultipartFile> documentation;

    private List<VersioningAppDto> versioningApplication;

    private ApplicationUrlDto applicationUrl;

    private String ipaFile;
    private String androidFile;

    
    private String[] sdaFrontEnd;

    
    private String[] sdaBackEnd;

    
    private String webServer;

    private SapIntegration sapIntegration;

    
    private String applicationSourceFrontEnd;

    
    private String applicationSourceBackEnd;

    
    private String databaseIp;

    private List<AppApiListDto> applicationApiList;

    private List<DbListDto> applicationDatabaseList;
}
