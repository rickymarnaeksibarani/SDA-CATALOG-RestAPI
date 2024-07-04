package sda.catalogue.sdacataloguerestapi.modules.WebApp.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Repositories.DocumentUploadRepository;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Repositories.FrontEndRepository;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Repositories.MappingFunctionRepository;
import sda.catalogue.sdacataloguerestapi.modules.PICAnalyst.Entities.PICAnalystEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICAnalyst.Repository.PICAnalystRepository;
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
import java.util.stream.Collectors;

@Slf4j
@Service
public class WebAppService extends BaseController {
    @Autowired
    private WebAppRepository webAppRepository;
    @Autowired
    private PICDeveloperRepository picDeveloperRepository;
    @Autowired
    private PICAnalystRepository picAnalystRepository;
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
    @Autowired
    private DocumentUploadRepository documentUploadRepository;
    @Autowired
    private ObjectMapper objectMapper;


    private final Date date = new Date();
    private final Long time = date.getTime();

    //Creating data WebApp
    @Transactional
    public WebAppEntity createWebApp(WebAppPostDTO request,
                                     List<Long> picDeveloper,
                                     List<Long> picAnalyst,
                                     List<Long> mappingFunction,
                                     List<Long> frontEnd,
                                     List<Long> backEnd,
                                     List<Long> webServer) {
        try {
            if (webAppRepository.existsByApplicationName(request.getApplicationName())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Application name already exists");
            }

            //PIC Developer Process
            List<PICDeveloperEntity> picDeveloperData = processLongList(picDeveloper, picDeveloperRepository, Function.identity(), "PIC Developer");

            //PIC Analyst Process
            List<PICAnalystEntity> picAnalystData = processLongList(picAnalyst, picAnalystRepository, Function.identity(), "PIC Analyst");

            //Mapping Function Process
            List<MappingFunctionEntity> mappingFunctionData = processLongList(mappingFunction, mappingFunctionRepository, Function.identity(), "Mapping Function");

            //Front End Process
            List<FrontEndEntity> frontEndData = processLongList(frontEnd, frontEndRepository, Function.identity(), "Front End");

            //Back End Process
            List<BackEndEntity> backEndData = processLongList(backEnd, backEndRepository, Function.identity(), "Back End");

            //Web Server Process
            List<WebServerEntity> webServerData = processLongList(webServer, webServerRepository, Function.identity(), "Web Server");

            //WebApp Process
            WebAppEntity data = ObjectMapperUtil.map(request, WebAppEntity.class);
            data.setApiApplicationList(objectMapper.writeValueAsString(request.getApiApplication()));
            data.setVersioningApplicationList(objectMapper.writeValueAsString(request.getVersioningApplicationList()));
            data.setDatabaseList(objectMapper.writeValueAsString(request.getDatabaseList()));
            data.setPicAnalyst(picAnalystData);
            data.setPicDeveloper(picDeveloperData);
            data.setMappingFunction(mappingFunctionData);
            data.setFrontEnd(frontEndData);
            data.setBackEnd(backEndData);
            data.setWebServer(webServerData);
            data.setApplicationName(request.getApplicationName());
            data.setSapIntegration(request.getSapIntegration());
            data.setDescription(request.getDescription());
            data.setAddress(request.getAddress());
            data.setIpDatabase(request.getIpDatabase());
            data.setFunctionApplication(request.getFunctionApplication());
            data.setApplicationSourceFe(request.getApplicationSourceFe());
            data.setApplicationSourceBe(request.getApplicationSourceBe());
            data.setAssetNumber(GenerateAssetNumber.generateAssetNumber("AW", webAppRepository.count() + 1));

            //SDA Hosting
            List<SDAHostingEntity> findSdaHosting = sdaHostingRepository.findByIdSDAHostingIsIn(request.getSdaHosting());
            if (!findSdaHosting.isEmpty()){
                findSdaHosting.forEach(data::setSdaHosting);
            }else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "sda hosting with IDs not found");
            }

            //Documentation process
            List<String> documentPaths = uploadDocument(request.getDocumentUploadList());
            List<DocumentUploadEntity> documentUploadEntities = new ArrayList<>();

            documentPaths.forEach(path -> {
                DocumentUploadEntity documentUploadEntity = new DocumentUploadEntity();
                documentUploadEntity.setPath(path);
                documentUploadEntity.setWebAppEntity(data);
                documentUploadEntities.add(documentUploadEntity);
            });
            data.setDocumentUploadList(documentUploadEntities);
            log.info("document {} ", request.getDocumentUploadList());
            WebAppEntity result = webAppRepository.save(data);

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
                                builder.like(builder.upper(root.get("assetNumber")), requestDto.getSearchTerm().toUpperCase())
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
        return webAppRepository.findById(id_webapp)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
    }

    //Updating data WebApp by id
    @Transactional
    public WebAppEntity updateWebAppById(Long idWebapp,
                                         WebAppPostDTO request,
                                         List<Long> picDeveloper,
                                         List<Long> picAnalyst,
                                         List<Long> mappingFunction,
                                         List<Long> frontEnd,
                                         List<Long> backEnd,
                                         List<Long> webServer
                                         ){
        try {
            WebAppEntity findData = webAppRepository.findById(idWebapp).orElse(null);
            if (findData == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "WebApp with ID : " + idWebapp + " not found");
            }

            if (!findData.getApplicationName().equals(request.getApplicationName())) {
                WebAppEntity existingApp = webAppRepository.findByApplicationName(request.getApplicationName());
                if (existingApp != null && !existingApp.getIdWebapp().equals(idWebapp)) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Application name already exist");
                }
            }

            // Handle existing documents
            List<DocumentUploadEntity> existingDocuments = findData.getDocumentUploadList();
            List<MultipartFile> newDocuments = request.getDocumentUploadList() != null ? request.getDocumentUploadList() : new ArrayList<>();

            if (existingDocuments != null) {
                List<String> newDocumentPaths = newDocuments.stream()
                        .map(MultipartFile::getOriginalFilename)
                        .toList();

                Iterator<DocumentUploadEntity> iterator = existingDocuments.iterator();
                while (iterator.hasNext()) {
                    DocumentUploadEntity existingDocument = iterator.next();
                    if (!newDocumentPaths.contains(existingDocument.getPath())) {
                        // Remove document from MinIO
                        try {
                            storageService.deleteAllFileS3(Collections.singletonList(existingDocument.getPath()));
                        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
                            throw new RuntimeException("Failed to delete file from MinIO", e);
                        }
                        // Remove document from database
                        documentUploadRepository.delete(existingDocument);
                        // Remove document from documentUploadList
                        iterator.remove();
                    }
                }
            }

            // Upload new documents and create document entities
            List<String> uploadedDocumentPaths = uploadDocument(newDocuments);
            List<DocumentUploadEntity> documentUploadEntities = new ArrayList<>();
            uploadedDocumentPaths.forEach(path -> {
                DocumentUploadEntity documentUploadEntity = new DocumentUploadEntity();
                documentUploadEntity.setPath(path);
                documentUploadEntity.setWebAppEntity(findData);
                documentUploadEntities.add(documentUploadEntity);
            });

            // Fetch data from data master
            List<PICDeveloperEntity> developer = picDeveloperRepository.findByIdPicDeveloperIsIn(picDeveloper);
            if (developer.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PIC Developer not found");
            }

            List<PICAnalystEntity> analyst = picAnalystRepository.findByIdPicAnalystIsIn(picAnalyst);
            if (analyst.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PIC Analyst not found");
            }

            List<MappingFunctionEntity> mapping = mappingFunctionRepository.findByIdMappingFunctionIsIn(mappingFunction);
            if (mapping.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mapping Function not found");
            }

            List<FrontEndEntity> fe = frontEndRepository.findByIdFrontEndIsIn(frontEnd);
            if (fe.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Front End not found");
            }

            List<BackEndEntity> be = backEndRepository.findByIdBackEndIsIn(backEnd);
            if (be.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Back End not found");
            }

            List<WebServerEntity> server = webServerRepository.findByIdWebServerIsIn(webServer);
            if (server.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Web Server not found");
            }

            List<SDAHostingEntity> findSdaHosting = sdaHostingRepository.findByIdSDAHostingIsIn(request.getSdaHosting());
            if (!findSdaHosting.isEmpty()) {
                findSdaHosting.forEach(findData::setSdaHosting);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "sda hosting with IDs not found");
            }

            findData.setAddress(request.getAddress());
            findData.setApplicationName(request.getApplicationName());
            findData.setVersioningApplicationList(objectMapper.writeValueAsString(request.getVersioningApplicationList()));
            findData.setApiApplicationList(objectMapper.writeValueAsString(request.getApiApplication()));
            findData.setDatabaseList(objectMapper.writeValueAsString(request.getDatabaseList()));
            findData.setDescription(request.getDescription());
            findData.setFunctionApplication(request.getFunctionApplication());
            findData.setBusinessImpactPriority(request.getBusinessImpactPriority());
            findData.setStatus(request.getStatus());
            findData.setSapIntegration(request.getSapIntegration());
            findData.setIpDatabase(request.getIpDatabase());
            findData.setPicDeveloper(developer);
            findData.setApplicationSourceBe(request.getApplicationSourceBe());
            findData.setApplicationSourceFe(request.getApplicationSourceFe());
            findData.setPicAnalyst(analyst);
            findData.setMappingFunction(mapping);
            findData.setFrontEnd(fe);
            findData.setBackEnd(be);
            findData.setWebServer(server);
            findData.setDocumentUploadList(documentUploadEntities);

            WebAppEntity result =  webAppRepository.save(findData);
            return result;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //Deleting data WebApp by UUID
    @Transactional
    public void deleteWebAppById(Long idWebapp){
        try {
            WebAppEntity findData = webAppRepository.findById(idWebapp).orElse(null);
            if (findData == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "WebApp with ID : " + idWebapp + " not found");
            }
            // Remove documents from MinIO and database if not referenced by other WebApp entities
            List<DocumentUploadEntity> documentUploadEntities = findData.getDocumentUploadList();
            if (documentUploadEntities != null && !documentUploadEntities.isEmpty()) {
                List<String> documentPaths = documentUploadEntities.stream()
                        .map(DocumentUploadEntity::getPath)
                        .collect(Collectors.toList());
                storageService.deleteAllFileS3(documentPaths);
            }

            // Menghapus entitas DocumentUpload dari database
            documentUploadRepository.deleteAll(documentUploadEntities);

            // Menghapus entitas WebApp dari database
            webAppRepository.delete(findData);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete the record. It is referenced by other record");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");
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

    private List<String> uploadDocument(List<MultipartFile> documents) {
        if (documents == null || documents.isEmpty()) return Collections.emptyList();

        List<String> documentPaths = new ArrayList<>();
        String generatedString = generateRandomString();

        documents.forEach(doc -> {
            if (!Objects.equals(doc.getContentType(), "application/pdf")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PDF Only");
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