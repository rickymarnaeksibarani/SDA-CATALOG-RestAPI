package sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto;

import jakarta.persistence.ElementCollection;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.DatabaseEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.VersioningApplicationEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Entities.WebServerEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebAppPostDTO {

    @NotEmpty
    private String applicationName;

    @NotEmpty
    private String categoryApp;

    @NotEmpty
    private String description;

    @NotEmpty
    private String functionApplication;

    @NotEmpty
    private String address;

    @NotEmpty
    private String businessImpactPriority;

    @NotEmpty
    private String status;

    private String linkIOS;

    private String linkAndroid;

    private MultipartFile fileManifest;

    private MultipartFile fileIpa;

    private MultipartFile fileAndroid;

    @NotEmpty
    private String applicationSourceFe;

    @NotEmpty
    private String applicationSourceBe;

    @NotEmpty
    private String ipDatabase;

    @NotEmpty
    private List<Integer> picDeveloperList;

    @NotEmpty
    private List<Integer> mappingFunctionList;

    @NotEmpty
    private List<Integer> frontEndList;

    @NotEmpty
    private List<Integer> backEndList;

    @NotEmpty
    private List<Integer> webServerList;

    @NotEmpty
    private List<VersioningApplicationEntity> versioningApplicationList;

    @NotEmpty
    private List<DatabaseEntity> databaseList;

    @NotEmpty
    private SDAHostingEntity sdaHostingEntity;

    @NotEmpty
    private List<MultipartFile> documentUploadList;
}
