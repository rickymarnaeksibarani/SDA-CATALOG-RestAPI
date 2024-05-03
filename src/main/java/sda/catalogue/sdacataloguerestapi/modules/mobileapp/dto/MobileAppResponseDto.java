package sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sda.catalogue.sdacataloguerestapi.core.enums.BusinessImpactPriority;
import sda.catalogue.sdacataloguerestapi.core.enums.Role;
import sda.catalogue.sdacataloguerestapi.core.enums.SapIntegration;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MobileAppResponseDto {
    private Long id;
    private String applicationName;
    private String pmoNumber;
    private Status status;
    private List<SDAHostingEntity> sdaHostingList;
    private List<MappingFunctionEntity> mappingFunctions;
    private List<Role> role;
    private BusinessImpactPriority businessImpactPriority;
    private List<PICDeveloperEntity> picDevelopers;
    private String description;
    private String applicationFunction;
    private List<String> documentation;
    private List<VersioningAppDto> versioningApplication;
    private ApplicationUrlDto applicationUrl;
    private List<String> applicationFilePath;
    private List<FrontEndEntity> sdaFrontEnds;
    private List<BackEndEntity> sdaBackEnds;
    private String webServer;
    private SapIntegration sapIntegration;
    private String applicationSourceFrontEnd;
    private String applicationSourceBackEnd;
    private String databaseIp;
    private List<AppApiListDto> applicationApiList;
    private List<DbListDto> applicationDatabaseList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
