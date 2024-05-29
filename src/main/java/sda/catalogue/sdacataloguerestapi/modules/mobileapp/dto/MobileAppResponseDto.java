package sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sda.catalogue.sdacataloguerestapi.core.enums.*;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICAnalyst.Entities.PICAnalystEntity;
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
    private String assetNumber;
    private Status status;
    private List<SDAHostingEntity> sdaHostingList;
    private List<MappingFunctionEntity> mappingFunctions;
    private List<Role> role;
    private BusinessImpactPriority businessImpactPriority;
    private List<PICDeveloperEntity> picDevelopers;
    private PICAnalystEntity picAnalyst;
    private String description;
    private String applicationFunction;
    private List<ApplicationFileDto> documentation;
    private List<VersioningAppDto> versioningApplication;
    private ApplicationUrlDto applicationUrl;
    private List<ApplicationFileDto> applicationFilePath;
    private List<FrontEndEntity> sdaFrontEnds;
    private List<BackEndEntity> sdaBackEnds;
    private String webServer;
    private SapIntegration sapIntegration;
    private String applicationSourceFrontEnd;
    private String applicationSourceBackEnd;
    private String databaseIp;
    private List<AppApiListDto> applicationApiList;
    private List<DbListDto> applicationDatabaseList;
    private AppCategory appCategory;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
