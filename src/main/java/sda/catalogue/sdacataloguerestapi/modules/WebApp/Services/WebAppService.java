package sda.catalogue.sdacataloguerestapi.modules.WebApp.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sda.catalogue.sdacataloguerestapi.core.BaseController;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.ObjectMapper.ObjectMapperUtil;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Repositories.BackEndRepository;
import sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Entities.DocumentUploadEntity;
import sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Services.DocumentUploadService;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Repositories.FrontEndRepository;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Repositories.MappingFunctionRepository;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Repositories.PICDeveloperRepository;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Repositories.SDAHostingRepository;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Entities.TypeDatabaseEntity;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Repositories.TypeDatabaseRepository;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.*;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.DatabaseEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.VersioningApplicationEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories.DatabaseRepository;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories.VersioningApplicationRepository;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories.WebAppRepository;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Entities.WebServerEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Repositories.WebServerRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Services.DocumentUploadService.UPLOAD_DIR;

@Service
public class WebAppService extends BaseController {

    @Autowired
    private WebAppRepository webAppRepository;
    @Autowired
    private PICDeveloperRepository picDeveloperRepository;
    @Autowired
    private MappingFunctionRepository mappingFunctionRepository;
    @Autowired
    private FrontEndRepository frontEndRepository;
    @Autowired
    private BackEndRepository backEndRepository;
    @Autowired
    private WebServerRepository webServerRepository;
    @Autowired
    private VersioningApplicationRepository versioningApplicationRepository;
    @Autowired
    private DatabaseRepository databaseRepository;
    @Autowired
    private DocumentUploadService documentUploadService;
    @Autowired
    private TypeDatabaseRepository typeDatabaseRepository;

    @Autowired
    private SDAHostingRepository sdaHostingRepository;

    private static final String UPLOAD_DIR_APK = "src/main/resources/uploads/apk";
    private static final String UPLOAD_DIR_IPA = "src/main/resources/uploads/ipa";
    private static final String UPLOAD_DIR_MANIFEST = "src/main/resources/uploads/manifest";


    //Getting data Web App with search and pagination
    public PaginationUtil<WebAppEntity, WebAppPostDTO> getAllWebAppByPagination(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Specification<WebAppEntity> specs = Specification.where(null);
        Page<WebAppEntity> pagedResult = webAppRepository.findAll(specs, paging);
        return new PaginationUtil<>(pagedResult, WebAppPostDTO.class);
    }

    //Getting data by UUID
    public WebAppEntity getWebAppByUuid(UUID uuid) {
        WebAppEntity result = webAppRepository.findByUuid(uuid);
        if (result == null) {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    //Creating data WebApp
    @Transactional
    public WebAppEntity createWebApp(WebAppPostDTO request, List<Long> picDeveloperList, List<Long> mappingFunctionList, List<Long> frontEndList, List<Long> backEndList, List<Long> webServerList, List<VersioningApplicationDTO> versioningApplicationList, List<DatabaseDTO> databaseList) {

        try {
            super.isValidApkType(request.getFileAndroid());
            String apkFileName = super.generateNewFilename(Objects.requireNonNull(request.getFileAndroid().getOriginalFilename()));
            Path newApkPath = Paths.get(UPLOAD_DIR_APK);
            Files.createDirectories(newApkPath);
            Path apkPath = newApkPath.resolve(apkFileName);
            Files.copy(request.getFileAndroid().getInputStream(), apkPath);

            //Ipa Process
            String ipaFileName = super.generateNewFilename(Objects.requireNonNull(request.getFileIpa().getOriginalFilename()));
            Path newIpaPath = Paths.get(UPLOAD_DIR_IPA);
            Files.createDirectories(newIpaPath);
            Path ipaPath = newIpaPath.resolve(ipaFileName);
            Files.copy(request.getFileIpa().getInputStream(), ipaPath);

            //Manifest Process
            String manifestFileName = super.generateNewFilename(Objects.requireNonNull(request.getFileManifest().getOriginalFilename()));
            Path newManifestPath = Paths.get(UPLOAD_DIR_MANIFEST);
            Files.createDirectories(newManifestPath);
            Path manifestPath = newManifestPath.resolve(manifestFileName);
            Files.copy(request.getFileManifest().getInputStream(), manifestPath);


            //PIC Developer Process
            List<PICDeveloperEntity> picDeveloperData = processLongList(picDeveloperList, picDeveloperRepository, Function.identity(), "PIC Developer");

            //Mapping Function Process
            List<MappingFunctionEntity> mappingFunctionData = processLongList(mappingFunctionList, mappingFunctionRepository, Function.identity(), "Mapping Function");

            //Front End Process
            List<FrontEndEntity> frontEndData = processLongList(frontEndList, frontEndRepository, Function.identity(), "Front End");

            //Back End Process
            List<BackEndEntity> backEndData = processLongList(backEndList, backEndRepository, Function.identity(), "Back End");

            //Web Server Process
            List<WebServerEntity> webServerData = processLongList(webServerList, webServerRepository, Function.identity(), "Web Server");


            //WebApp Process
            WebAppEntity data = ObjectMapperUtil.map(request, WebAppEntity.class);

            data.setPicDeveloperList(picDeveloperData);
            data.setMappingFunctionList(mappingFunctionData);
            data.setFrontEndList(frontEndData);
            data.setBackEndList(backEndData);
            data.setWebServerList(webServerData);
            //Path File
            data.setFileAndroid(String.valueOf(apkPath));
            data.setFileIpa(String.valueOf(ipaPath));
            data.setFileManifest(String.valueOf(manifestPath));

            // Modify SDA Hosting
            Optional<SDAHostingEntity> findSdaHosting = sdaHostingRepository.findById(request.getSdaHostingEntity());
            if (findSdaHosting.isPresent()) {
                data.setSdaHostingEntity(findSdaHosting.get());
            } else {
                throw new CustomRequestException("SDA Hosting with ID : " + request.getSdaHostingEntity() + " not found", HttpStatus.NOT_FOUND);
            }

            WebAppEntity result = webAppRepository.save(data);

            //Document Process
            documentUploadService.createDocumentUpload(request.getDocumentUploadList(), result.getIdWebapp());

            //Versioning Application Process
            List<VersioningApplicationEntity> versioningApplicationListData = new ArrayList<>();
            for (VersioningApplicationDTO versioningApplicationId : versioningApplicationList) {
                VersioningApplicationEntity versioningApplicationItem = new VersioningApplicationEntity();
                versioningApplicationItem.setVersion(versioningApplicationId.getVersion());
                versioningApplicationItem.setDescription(versioningApplicationId.getDescription());
                versioningApplicationItem.setReleaseDate(versioningApplicationId.getReleaseDate());
                versioningApplicationItem.setWebAppEntity(result);
                versioningApplicationListData.add(versioningApplicationItem);
            }

            //Database Process
            List<DatabaseEntity> databaseListData = new ArrayList<>();
            for (DatabaseDTO databaseId : databaseList) {
                DatabaseEntity databaseItem = new DatabaseEntity();
                databaseItem.setWebAppEntity(result);
                databaseItem.setApiAddress(databaseId.getApiAddress());
                databaseItem.setPassword(databaseId.getPassword());
                databaseItem.setApiName(databaseId.getApiName());
                Optional<TypeDatabaseEntity> typeDatabaseEntityOptional = typeDatabaseRepository.findById(databaseId.getIdTypeDatabase());
                if (typeDatabaseEntityOptional.isPresent()) {
                    TypeDatabaseEntity typeDatabaseEntity = typeDatabaseEntityOptional.get();
                    databaseItem.setTypeDatabaseEntity(typeDatabaseEntity);
                    databaseListData.add(databaseItem);
                } else {
                    throw new CustomRequestException("Database with ID : " + databaseId.getIdTypeDatabase() + " not found", HttpStatus.NOT_FOUND);
                }
            }

            databaseRepository.saveAll(databaseListData);
            versioningApplicationRepository.saveAll(versioningApplicationListData);
            return result;
        } catch (IOException e) {
            throw new CustomRequestException(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }


    //Updating data WebApp by UUID
    public WebAppEntity updateWebAppByUuid(UUID uuid, WebAppPostDTO request, List<Long> picDeveloperList, List<Long> mappingFunctionList, List<Long> frontEndList, List<Long> backEndList, List<Long> webServerList, List<VersioningApplicationDTO> versioningApplicationList, List<DatabaseDTO> databaseList) {
        try {
            WebAppEntity findData = webAppRepository.findByUuid(uuid);
            if (findData == null) {
                throw new CustomRequestException("WebApp with UUID : " + uuid + " not found", HttpStatus.NOT_FOUND);
            }
            super.isValidApkType(request.getFileAndroid());
            String apkFileName = super.generateNewFilename(Objects.requireNonNull(request.getFileAndroid().getOriginalFilename()));
            Path newApkPath = Paths.get(UPLOAD_DIR_APK);
            Files.createDirectories(newApkPath);
            Path apkPath = newApkPath.resolve(apkFileName);
            Files.copy(request.getFileAndroid().getInputStream(), apkPath);

            //Ipa Process
            String ipaFileName = super.generateNewFilename(Objects.requireNonNull(request.getFileIpa().getOriginalFilename()));
            Path newIpaPath = Paths.get(UPLOAD_DIR_IPA);
            Files.createDirectories(newIpaPath);
            Path ipaPath = newIpaPath.resolve(ipaFileName);
            Files.copy(request.getFileIpa().getInputStream(), ipaPath);

            //Manifest Process
            String manifestFileName = super.generateNewFilename(Objects.requireNonNull(request.getFileManifest().getOriginalFilename()));
            Path newManifestPath = Paths.get(UPLOAD_DIR_MANIFEST);
            Files.createDirectories(newManifestPath);
            Path manifestPath = newManifestPath.resolve(manifestFileName);
            Files.copy(request.getFileManifest().getInputStream(), manifestPath);

            webAppRepository.updateByUuid(
                    uuid,
                    request.getApplicationName(),
                    request.getCategoryApp(),
                    request.getDescription(),
                    request.getFunctionApplication(),
                    request.getAddress(),
                    request.getBusinessImpactPriority(),
                    request.getStatus(),
                    request.getLinkIOS(),
                    request.getLinkAndroid(),
                    manifestPath.toString(),
                    ipaPath.toString(),
                    apkPath.toString(),
                    request.getApplicationSourceFe(),
                    request.getApplicationSourceBe(),
                    request.getIpDatabase()
            );
            return webAppRepository.findByUuid(uuid);
        } catch (IOException e) {
            throw new CustomRequestException(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    //Deleting data WebApp by UUID
    public WebAppEntity deleteWebAppByUuid(UUID uuid) {
        return webAppRepository.findByUuidAndDelete(uuid);
    }


    private void deleteApkIpaManifest(Path apkPath, Path ipaPath, Path manifestPath) {
        try {
            //APK Process
            Files.delete(apkPath);

            //Ipa Process
            Files.delete(ipaPath);

            //Manifest Process
            Files.delete(manifestPath);
        } catch (IOException e) {
            throw new CustomRequestException(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    public SDAStatusStatsDTO statsWebByStatus() {
        int active = webAppRepository.countByStatus("active");
        int underConstruction = webAppRepository.countByStatus("Under Construction");
        int underReview = webAppRepository.countByStatus("Under Review");
        int inactive = webAppRepository.countByStatus("Inactive");
        SDAStatusStatsDTO stats = new SDAStatusStatsDTO();
        stats.setActive(active);
        stats.setUnderConstruction(underConstruction);
        stats.setUnderReview(underReview);
        stats.setInactive(inactive);
        return stats;
    }

    public List<SDAHostingStatsDTO> statsSdaHostingArray() {
        List<SDAHostingStatsDTO> statsList = new ArrayList<>();
        for (SDAHostingEntity sdaHosting : sdaHostingRepository.findAll()) {
            SDAHostingStatsDTO dataModified = new SDAHostingStatsDTO();
            dataModified.setName(sdaHosting.getSdaHosting());
            dataModified.setTotal(webAppRepository.countBySdaHosting(sdaHosting.getSdaHosting()));
            statsList.add(dataModified);
        }
        return statsList;
    }

    public Map<String, Long> statsSdaHostingObject() {
        Map<String, Long> statsMap = new HashMap<>();
        for (SDAHostingEntity sdaHosting : sdaHostingRepository.findAll()) {
            String hostingName = sdaHosting.getSdaHosting().replace(" ", ""); // Remove spaces
            long total = webAppRepository.countBySdaHosting(sdaHosting.getSdaHosting());
            statsMap.put(hostingName, total);
        }
        return statsMap;
    }
}
