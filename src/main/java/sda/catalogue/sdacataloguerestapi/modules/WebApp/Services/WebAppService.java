package sda.catalogue.sdacataloguerestapi.modules.WebApp.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.minio.ObjectWriteResponse;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sda.catalogue.sdacataloguerestapi.core.BaseController;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.ObjectMapper.ObjectMapperUtil;
import sda.catalogue.sdacataloguerestapi.core.enums.BusinessImpactPriority;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Repositories.BackEndRepository;
import sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Services.DocumentUploadService;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Repositories.FrontEndRepository;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Repositories.MappingFunctionRepository;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Repositories.PICDeveloperRepository;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Repositories.SDAHostingRepository;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Entities.TypeDatabaseEntity;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Repositories.TypeDatabaseRepository;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.*;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.ApiEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.DatabaseEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.VersioningApplicationEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories.ApiRepository;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories.DatabaseRepository;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories.VersioningApplicationRepository;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories.WebAppRepository;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Entities.WebServerEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Repositories.WebServerRepository;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto.UserFilterRequest;
import sda.catalogue.sdacataloguerestapi.modules.storage.StorageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

@Slf4j
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
    private ApiRepository apiRepository;
    @Autowired
    private DocumentUploadService documentUploadService;
    @Autowired
    private TypeDatabaseRepository typeDatabaseRepository;
    @Autowired
    private SDAHostingRepository sdaHostingRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StorageService storageService;



    private static final String UPLOAD_DIR_APK = "src/main/resources/uploads/apk";
    private static final String UPLOAD_DIR_IPA = "src/main/resources/uploads/ipa";
    private static final String UPLOAD_DIR_MANIFEST = "src/main/resources/uploads/manifest";
    private final Date date = new Date();
    private final Long time = date.getTime();

    //Creating data WebApp
    @Transactional
    public WebAppEntity createWebApp(WebAppPostDTO request,
                                     List<Long> picDeveloperList,
                                     List<Long> mappingFunctionList,
                                     List<Long> frontEndList,
                                     List<Long> backEndList,
                                     List<Long> webServerList,
                                     List<VersioningApplicationDTO> versioningApplicationList,
                                     List<DatabaseDTO> databaseList,
                                     List<ApiDTO> apiList) {
        try {
            if (webAppRepository.existsByApplicationName(request.getApplicationName())) {
                throw new CustomRequestException("Application name already exists", HttpStatus.CONFLICT);
            }
            //File Android Process
            Path apkPath = null;
            if (request.getFileAndroid() != null) {
                super.isValidApkType(request.getFileAndroid());
                String apkFileName = super.generateNewFilename(Objects.requireNonNull(request.getFileAndroid().getOriginalFilename()));
                Path newApkPath = Paths.get(UPLOAD_DIR_APK);
                Files.createDirectories(newApkPath);
                apkPath = newApkPath.resolve(apkFileName);
                Files.copy(request.getFileAndroid().getInputStream(), apkPath);
            }
            //Ipa Process
            Path ipaPath = null;
            if (request.getFileIpa() != null) {
                String ipaFileName = super.generateNewFilename(Objects.requireNonNull(request.getFileIpa().getOriginalFilename()));
                Path newIpaPath = Paths.get(UPLOAD_DIR_IPA);
                Files.createDirectories(newIpaPath);
                ipaPath = newIpaPath.resolve(ipaFileName);
                Files.copy(request.getFileIpa().getInputStream(), ipaPath);
            }

            //Manifest Process
            Path manifestPath = null;
            if (request.getFileManifest() != null) {
                String manifestFileName = super.generateNewFilename(Objects.requireNonNull(request.getFileManifest().getOriginalFilename()));
                Path newManifestPath = Paths.get(UPLOAD_DIR_MANIFEST);
                Files.createDirectories(newManifestPath);
                manifestPath = newManifestPath.resolve(manifestFileName);
                Files.copy(request.getFileManifest().getInputStream(), manifestPath);
            }

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

            //SDA Hosting
            List<SDAHostingEntity> findSdaHosting = sdaHostingRepository.findByIdSDAHostingIsIn(request.getSdaHosting());
            if (!findSdaHosting.isEmpty()) {
                List<Long> sdaHostingId = new ArrayList<>();
                findSdaHosting.forEach(hostingData -> sdaHostingId.add(hostingData.getIdSDAHosting()));
                request.setSdaHosting(sdaHostingId);
            } else {
                throw new CustomRequestException("SDA Hosting with ID : " + request.getSdaHosting() + " not found", HttpStatus.NOT_FOUND);
            }

            //WebApp Process
            WebAppEntity data = ObjectMapperUtil.map(request, WebAppEntity.class);

            data.setPicDeveloperList(picDeveloperData);
            data.setMappingFunctionList(mappingFunctionData);
            data.setFrontEndList(frontEndData);
            data.setBackEndList(backEndData);
            data.setWebServerList(webServerData);


            //Path File
            if (apkPath != null) {
                data.setFileAndroid(String.valueOf(apkPath));
            }
            if (ipaPath != null) {
                data.setFileIpa(String.valueOf(ipaPath));
            }
            if (manifestPath != null) {
                data.setFileManifest(String.valueOf(manifestPath));
            }

            WebAppEntity result = webAppRepository.save(data);

            //Document Process
            if (request.getDocumentUploadList() != null) {
                documentUploadService.createDocumentUpload(request.getDocumentUploadList(), result.getIdWebapp());
            }

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

            //API Process
            List<ApiEntity> apiListData = new ArrayList<>();
            for (ApiDTO apiId : apiList) {
                ApiEntity apiItem = new ApiEntity();
                apiItem.setApiName(apiId.getApiName());
                apiItem.setIpApi(apiId.getIpApi());
                apiItem.setUserName(apiId.getUserName());
                apiItem.setPassword(apiId.getPassword());
                apiItem.setWebAppEntity(result);
                apiListData.add(apiItem);
            }

            //Database Process
            List<DatabaseEntity> databaseListData = new ArrayList<>();
            for (DatabaseDTO databaseId : databaseList) {
                DatabaseEntity databaseItem = new DatabaseEntity();
                databaseItem.setWebAppEntity(result);
                databaseItem.setDbAddress(databaseId.getDbAddress());
                databaseItem.setDbPassword(databaseId.getDbPassword());
                databaseItem.setDbName(databaseId.getDbName());
                databaseItem.setDbUserName(databaseId.getDbUserName());
                Optional<TypeDatabaseEntity> typeDatabaseEntityOptional = typeDatabaseRepository.findById(databaseId.getIdTypeDatabase());
                if (typeDatabaseEntityOptional.isPresent()) {
                    TypeDatabaseEntity typeDatabaseEntity = typeDatabaseEntityOptional.get();
                    databaseItem.setTypeDatabaseEntity(typeDatabaseEntity);
                    databaseListData.add(databaseItem);
                } else {
                    throw new CustomRequestException("Database with ID : " + databaseId.getIdTypeDatabase() + " not found", HttpStatus.NOT_FOUND);
                }
            }

            apiRepository.saveAll(apiListData);
            databaseRepository.saveAll(databaseListData);
            versioningApplicationRepository.saveAll(versioningApplicationListData);
            return result;
        } catch (IOException e) {
            throw new CustomRequestException(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    //Getting data Web App with search and pagination
    @Transactional(readOnly = true)
    public PaginationUtil<WebAppEntity, WebAppEntity> getAllWebApp(WebAppRequestDto requestDto) {
        Specification<WebAppEntity> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(requestDto.getSearchTerm())) {
                predicates.add(
                        builder.or(
                                builder.like(builder.upper(root.get("applicationName")), "%" + requestDto.getSearchTerm().toUpperCase() + "%"),
                                builder.like(builder.upper(root.get("pmoNumber")), "%" + requestDto.getSearchTerm().toUpperCase() + "%")
                        )
                );
            }

            if (Objects.nonNull(requestDto.getFilterByStatus()) && !requestDto.getFilterByStatus().isEmpty()) {
                predicates.add(
                        builder.in(root.get("status")).value(requestDto.getFilterByStatus())
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };


        PageRequest pageRequest = PageRequest.of(requestDto.getPage() - 1, requestDto.getSize(), Sort.by(Sort.Order.desc("createdAt")));
        Page<WebAppEntity> webApp = webAppRepository.findAll(specification, pageRequest);

        return new PaginationUtil<>(webApp, WebAppEntity.class);

    }



    //Getting data by ID
    public WebAppEntity getWebAppById(Long id_webapp) throws JsonProcessingException {
        WebAppEntity result = webAppRepository.findById(id_webapp)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));

        List<Long> sdaHostingId = objectMapper.readValue(result.getSdaHosting(), new TypeReference<>() {});
        List<SDAHostingEntity> sdaHostingList = sdaHostingRepository.findByIdSDAHostingIsIn(sdaHostingId);
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode sdaHostingArray = mapper.createArrayNode();
        for (SDAHostingEntity sdaHosting : sdaHostingList) {
            ObjectNode hostingNode = mapper.createObjectNode();
            hostingNode.put("idSdaHosting", sdaHosting.getIdSDAHosting());
            hostingNode.put("uuid", sdaHosting.getUuid().toString());
            hostingNode.put("sdaHosting", sdaHosting.getSdaHosting());
            hostingNode.put("createdAt", sdaHosting.getCreatedAt().toString());
            hostingNode.put("updatedAt", sdaHosting.getUpdatedAt().toString());
            sdaHostingArray.add(hostingNode);
        }

        result.setSdaHosting(sdaHostingArray.toString());

        return result;
    }

    //Updating data WebApp by UUID
    public WebAppEntity updateWebAppByUuid(UUID uuid,
                                           WebAppPostDTO request,
                                           List<Long> picDeveloperList,
                                           List<Long> mappingFunctionList,
                                           List<Long> frontEndList,
                                           List<Long> backEndList,
                                           List<Long> webServerList,
                                           List<VersioningApplicationEntity> versioningApplicationEntities,
                                           List<DatabaseEntity> databaseEntities,
                                           List<ApiEntity> apiEntities

    ) {
        try {
            WebAppEntity findData = webAppRepository.findByUuid(uuid);
            if (findData == null) {
                throw new CustomRequestException("WebApp with UUID : " + uuid + " not found", HttpStatus.NOT_FOUND);
            }

            if (!findData.getApplicationName().equals(request.getApplicationName())){
                WebAppEntity existingApp = webAppRepository.findByApplicationName(request.getApplicationName());
                if (existingApp != null && !existingApp.getUuid().equals(uuid)){
                    throw new CustomRequestException("Application name already exist", HttpStatus.CONFLICT);
                }
            }

            MultipartFile fileAndroid = request.getFileAndroid();
            MultipartFile fileIpa = request.getFileIpa();
            MultipartFile fileManifest = request.getFileManifest();


            //Fetching from data master
            List<PICDeveloperEntity> picDeveloper = picDeveloperRepository.findByIdPicDeveloperIsIn(picDeveloperList);
            if (picDeveloper.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PIC Developer not found");
            }
            List<MappingFunctionEntity> mappingFunction = mappingFunctionRepository.findByIdMappingFunctionIsIn(mappingFunctionList);
            if (mappingFunction.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mapping Function not found");
            }

            List<FrontEndEntity> frontEnd = frontEndRepository.findByIdFrontEndIsIn(frontEndList);
            if (frontEnd.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Front End not found");
            }

            List<BackEndEntity> backEnd = backEndRepository.findByIdBackEndIsIn(backEndList);
            if (backEnd.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Back End not found");
            }

            List<WebServerEntity> webServer = webServerRepository.findByIdWebServerIsIn(webServerList);
            if (webServer.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Web Server not found");
            }

            List<SDAHostingEntity> findSdaHosting = sdaHostingRepository.findByIdSDAHostingIsIn(request.getSdaHosting());
            if (!findSdaHosting.isEmpty()){
                findData.setSdaHosting(findSdaHosting.toString());
            }else {
                throw new CustomRequestException("sda hosting with IDs not found", HttpStatus.NOT_FOUND);
            }


            Path apkPath;
            Path ipaPath;
            Path manifestPath;

            //Apk Andorid Process
            if (fileAndroid != null) {
                super.isValidApkType(fileAndroid);
                String apkFileName = super.generateNewFilename(Objects.requireNonNull(fileAndroid.getOriginalFilename()));
                Path newApkPath = Paths.get(UPLOAD_DIR_APK);
                Files.createDirectories(newApkPath);
                apkPath = newApkPath.resolve(apkFileName);
                Files.copy(fileAndroid.getInputStream(), apkPath);
            }

            //Ipa Process
            if (fileIpa != null) {
                String ipaFileName = super.generateNewFilename(Objects.requireNonNull(fileIpa.getOriginalFilename()));
                Path newIpaPath = Paths.get(UPLOAD_DIR_IPA);
                Files.createDirectories(newIpaPath);
                ipaPath = newIpaPath.resolve(ipaFileName);
                Files.copy(fileIpa.getInputStream(), ipaPath);
            }

            //Manifest Process
            if (fileManifest != null) {
                String manifestFileName = super.generateNewFilename(Objects.requireNonNull(fileManifest.getOriginalFilename()));
                Path newManifestPath = Paths.get(UPLOAD_DIR_MANIFEST);
                Files.createDirectories(newManifestPath);
                manifestPath = newManifestPath.resolve(manifestFileName);
                Files.copy(fileManifest.getInputStream(), manifestPath);
            }

            findData.setCategoryApp(request.getCategoryApp());
            findData.setAddress(request.getAddress());
            findData.setApplicationName(request.getApplicationName());
            findData.setLinkIOS(request.getLinkIOS());
            findData.setLinkAndroid(request.getLinkAndroid());
            findData.setFileIpa(String.valueOf(request.getFileIpa()));
            findData.setFileAndroid(String.valueOf(request.getFileAndroid()));
            findData.setFileManifest(String.valueOf(request.getFileManifest()));
            findData.setDescription(request.getDescription());
            findData.setFunctionApplication(request.getFunctionApplication());
            findData.setBusinessImpactPriority(request.getBusinessImpactPriority());
            findData.setStatus(request.getStatus());
            findData.setSapIntegration(request.getSapIntegration());
            findData.setIpDatabase(request.getIpDatabase());
            findData.setPmoNumber(request.getPmoNumber());
            findData.setPicDeveloperList(picDeveloper);
            findData.setMappingFunctionList(mappingFunction);
            findData.setFrontEndList(frontEnd);
            findData.setBackEndList(backEnd);
            findData.setWebServerList(webServer);
            findData.setVersioningApplicationList(versioningApplicationEntities);
            findData.setDatabaseList(databaseEntities);
            findData.setApiList(apiEntities);

            webAppRepository.save(findData);

            return webAppRepository.findByUuid(uuid);
        } catch (IOException e) {
            throw new CustomRequestException(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    //Deleting data WebApp by UUID
    @Transactional
    public void deleteWebAppByUuid(UUID uuid){
        try {
            webAppRepository.findByUuidAndDelete(uuid);
        }
        catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete the record. It is referenced by other record");
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Data not found");
        }
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
            statsList.add(dataModified);
        }
        return statsList;
    }

    public Map<String, Long> statsSdaHostingObject() {
        Map<String, Long> statsMap = new HashMap<>();
        for (SDAHostingEntity sdaHosting : sdaHostingRepository.findAll()) {
            String hostingName = sdaHosting.getSdaHosting().replace(" ", "");
        }
        return statsMap;
    }

    private  String generateRandomString(){
        Random random = new Random();
        return random.ints(97, 122+1)
                .limit(11)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private List<String> uploadDocument(List<MultipartFile> documents){
        if (documents == null || documents.isEmpty()) return null;

        List<String> documentPaths = new ArrayList<>();
        String generatedString = generateRandomString();

        documents.forEach(doc -> {
            try {
                String docFilename = time + generatedString + "_" + Objects.requireNonNull(doc.getOriginalFilename()).replace(" ", "_");
//                Path docFileDestination = Files.createDirectories(Path.of(uploadpath + "/document/"));
//                doc.transferTo(Path.of(docFileDestination + "/" + docFilename));
//                documentPaths.add(docFileDestination + "/" + docFilename);

                String filepath = LocalDate.now().getYear() + "/docs/" + docFilename;
                ObjectWriteResponse objectWriteResponse = storageService.storeToS3(filepath, doc);
                documentPaths.add(objectWriteResponse.object());
            } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        });
        return documentPaths;
    }
    private String uploadFileApp(MultipartFile file, String appFileCategory) throws Exception {
        if (file == null || file.isEmpty()) return null;

        String filePaths = null;
        String generatedString = generateRandomString();

        if (Objects.equals(appFileCategory, "apk")) {
            if (!Objects.equals(file.getContentType(), "application/vnd.android.package-archive")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid android file");
            }

            String androidFilename = time + generatedString + "_" + Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");
//            Path fileDestination = Files.createDirectories(Path.of(uploadpath + "/apk/"));
//            Path resolve = fileDestination.resolve(androidFilename.trim());
//            Files.copy(file.getInputStream(), resolve);
//            filePaths = String.valueOf(resolve);

            filePaths = LocalDate.now().getYear() + "/android/" + androidFilename;
        }

        if (Objects.equals(appFileCategory, "ipa")) {
//            if (!Objects.equals(file.getOriginalFilename(), ".ipa")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ipa file");

            String ipaFilename = time + generatedString + "_" + Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");
//            Path fileDestination = Files.createDirectories(Path.of(uploadpath + "/ipa/"));
//            Path resolve = fileDestination.resolve(ipaFilename.trim());
//            Files.copy(file.getInputStream(), resolve);
//            filePaths = String.valueOf(resolve);

            filePaths = LocalDate.now().getYear() + "/ipa/" + ipaFilename;
        }

        ObjectWriteResponse objectWriteResponse = storageService.storeToS3(filePaths, file);
        return objectWriteResponse.object();
    }






}
