package sda.catalogue.sdacataloguerestapi.modules.WebApp.Services;

import io.minio.ObjectWriteResponse;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import sda.catalogue.sdacataloguerestapi.core.utils.GenerateAssetNumber;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Repositories.BackEndRepository;
import sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Entities.DocumentUploadEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Repositories.FrontEndRepository;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Repositories.MappingFunctionRepository;
//import sda.catalogue.sdacataloguerestapi.modules.PICAnalyst.Entities.PICAnalystEntity;
//import sda.catalogue.sdacataloguerestapi.modules.PICAnalyst.Repository.PICAnalystRepository;
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
import sda.catalogue.sdacataloguerestapi.modules.storage.StorageService;

import java.io.IOException;
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
    //    @Autowired
//    private PICAnalystRepository picAnalystRepository;
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
    private TypeDatabaseRepository typeDatabaseRepository;
    @Autowired
    private SDAHostingRepository sdaHostingRepository;
    @Autowired
    private StorageService storageService;


    private final Date date = new Date();
    private final Long time = date.getTime();

    //Creating data WebApp
    @Transactional
    public WebAppEntity createWebApp(WebAppPostDTO request,
                                     List<Long> picDeveloperList,
//                                     List<Long> picAnalystList,
                                     List<Long> mappingFunctionList,
                                     List<Long> frontEndList,
                                     List<Long> backEndList,
                                     List<Long> webServerList,
                                     List<VersioningApplicationDTO> versioningApplicationList,
                                     List<DatabaseDTO> databaseList,
                                     List<ApiDTO> apiList) {
        try {
            if (webAppRepository.existsByApplicationName(request.getApplicationName())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Application name already exists");
            }

            //PIC Developer Process
            List<PICDeveloperEntity> picDeveloperData = processLongList(picDeveloperList, picDeveloperRepository, Function.identity(), "PIC Developer");

            //PIC Analyst Process
//            List<PICAnalystEntity> picAnalystData = processLongList(picAnalystList, picAnalystRepository, Function.identity(), "PIC Analyst");

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
//            data.setPicAnalystList(picAnalystData);
            data.setMappingFunctionList(mappingFunctionData);
            data.setFrontEndList(frontEndData);
            data.setBackEndList(backEndData);
            data.setWebServerList(webServerData);

            //SDA Hosting
            List<SDAHostingEntity> findSdaHosting = sdaHostingRepository.findByIdSDAHostingIsIn(request.getSdaHosting());

            if (!findSdaHosting.isEmpty()){
                findSdaHosting.stream().forEach(data::setSdaHostingEntity);
            }else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "sda hosting with IDs not found");
            }


            //Documentation process
            List<String> documentPaths = uploadDocument(request.getDocumentUploadList());
            List<DocumentUploadEntity> documentUploadEntities = new ArrayList<>();

            if (documentPaths != null){
                documentPaths.stream().forEach(path -> {
                    DocumentUploadEntity documentUploadEntity = new DocumentUploadEntity();
                    documentUploadEntity.setPath(path);
                    documentUploadEntity.setWebAppEntity(data);
                    documentUploadEntities.add(documentUploadEntity);
                });
            }
            data.setDocumentUploadList(documentUploadEntities);
            data.setAssetNumber(GenerateAssetNumber.generateAssetNumber("AW", webAppRepository.count() + 1));
            WebAppEntity result = webAppRepository.save(data);

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
        } catch (Exception e) {
            throw new RuntimeException(e);
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
                                builder.like(builder.upper(root.get("assetNumber")), "%" + requestDto.getSearchTerm().toUpperCase() + "%")
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
    public WebAppEntity getWebAppById(Long id_webapp) {
        WebAppEntity result = webAppRepository.findById(id_webapp)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        return result;
    }

    //Updating data WebApp by UUID
    public WebAppEntity updateWebAppByUuid(UUID uuid,
                                           WebAppPostDTO request,
                                           List<Long> picDeveloperList,
//                                           List<Long> picAnalystList,
                                           List<Long> mappingFunctionList,
                                           List<Long> frontEndList,
                                           List<Long> backEndList,
                                           List<Long> webServerList,
                                           List<VersioningApplicationEntity> versioningApplicationEntities,
                                           List<DatabaseEntity> databaseEntities,
                                           List<ApiEntity> apiEntities

    ) {
        WebAppEntity findData = webAppRepository.findByUuid(uuid);
        if (findData == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "WebApp with UUID : " + uuid + " not found");
        }

        if (!findData.getApplicationName().equals(request.getApplicationName())){
            WebAppEntity existingApp = webAppRepository.findByApplicationName(request.getApplicationName());
            if (existingApp != null && !existingApp.getUuid().equals(uuid)){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"Application name already exist");
            }
        }

        //Update new Documentation
        List<String> documentPaths = uploadDocument(request.getDocumentUploadList());
        List<DocumentUploadEntity> documentUploadEntities = new ArrayList<>();

        WebAppEntity data = ObjectMapperUtil.map(request, WebAppEntity.class);
        if (documentPaths != null){
            documentPaths.stream().forEach(path -> {
                DocumentUploadEntity documentUploadEntity = new DocumentUploadEntity();
                documentUploadEntity.setPath(path);
                documentUploadEntity.setWebAppEntity(data);
                documentUploadEntities.add(documentUploadEntity);
            });
        }

        //Fetching from data master
        List<PICDeveloperEntity> picDeveloper = picDeveloperRepository.findByIdPicDeveloperIsIn(picDeveloperList);
        if (picDeveloper.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PIC Developer not found");
        }

//            List<PICAnalystEntity> picAnalyst = picAnalystRepository.findByIdPicAnalystIsIn(picAnalystList);
//            if (picAnalyst.isEmpty()){
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PIC Analyst not found");
//            }

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
            findSdaHosting.stream().forEach(findData::setSdaHostingEntity);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "sda hosting with IDs not found");
        }

        findData.setAddress(request.getAddress());
        findData.setApplicationName(request.getApplicationName());
        findData.setLinkIOS(request.getLinkIOS());
        findData.setLinkAndroid(request.getLinkAndroid());
        findData.setDescription(request.getDescription());
        findData.setFunctionApplication(request.getFunctionApplication());
        findData.setBusinessImpactPriority(request.getBusinessImpactPriority());
        findData.setStatus(request.getStatus());
        findData.setSapIntegration(request.getSapIntegration());
        findData.setIpDatabase(request.getIpDatabase());
        findData.setPicDeveloperList(picDeveloper);
//            findData.setPicAnalystList(picAnalyst);
        findData.setMappingFunctionList(mappingFunction);
        findData.setFrontEndList(frontEnd);
        findData.setBackEndList(backEnd);
        findData.setWebServerList(webServer);
        findData.setVersioningApplicationList(versioningApplicationEntities);
        findData.setDatabaseList(databaseEntities);
        findData.setApiList(apiEntities);
        findData.setDocumentUploadList(documentUploadEntities);

        webAppRepository.save(findData);

        return webAppRepository.save(findData);
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
        if (documents == null || documents.isEmpty()) return Collections.emptyList();

        List<String> documentPaths = new ArrayList<>();
        String generatedString = generateRandomString();

        documents.forEach(doc -> {
            if (!Objects.equals(doc.getContentType(), "application/pdf")){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"PDF Only");
            }
            try {
                String docFilename = time + generatedString + "_" + Objects.requireNonNull(doc.getOriginalFilename()).replace(" ", "_");

                String filepath = LocalDate.now().getYear() + "/docs/" + docFilename;
                ObjectWriteResponse objectWriteResponse = storageService.storeToS3(filepath, doc);
                documentPaths.add(objectWriteResponse.object());
            } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        });
        return documentPaths;
    }






}
