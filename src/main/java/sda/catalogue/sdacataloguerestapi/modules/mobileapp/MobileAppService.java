package sda.catalogue.sdacataloguerestapi.modules.mobileapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.ObjectWriteResponse;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sda.catalogue.sdacataloguerestapi.core.enums.Role;
import sda.catalogue.sdacataloguerestapi.core.utils.GenerateAssetNumber;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Repositories.BackEndRepository;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Repositories.FrontEndRepository;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Repositories.MappingFunctionRepository;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Repositories.PICDeveloperRepository;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Repositories.SDAHostingRepository;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto.*;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.entity.MobileAppEntity;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.repository.MobileAppRepository;
import sda.catalogue.sdacataloguerestapi.modules.storage.StorageService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class MobileAppService {
    @Autowired
    private StorageService storageService;
    @Autowired
    private MobileAppRepository mobileAppRepository;
    @Autowired
    private MappingFunctionRepository mappingFunctionRepository;
    @Autowired
    private PICDeveloperRepository picDeveloperRepository;
    @Autowired
    private SDAHostingRepository sdaHostingRepository;
    @Autowired
    private FrontEndRepository frontEndRepository;
    @Autowired
    private BackEndRepository backEndRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private final Path uploadpath = Paths.get("src/main/resources/uploads/");
    private final Date date = new Date();
    private final Long time = date.getTime();

    private MobileAppResponseDto toMobileAppResponse(MobileAppEntity mobileApp) throws JsonProcessingException {
        List<AppApiListDto> appApiList = objectMapper.readValue(mobileApp.getApplicationApiList(), new TypeReference<>() {});
        List<DbListDto> dbList = objectMapper.readValue(mobileApp.getApplicationDatabaseList(), new TypeReference<>() {});
        ApplicationUrlDto applicationUrl = objectMapper.readValue(mobileApp.getApplicationUrl(), new TypeReference<>() {});
        List<Role> roles = objectMapper.readValue(mobileApp.getRole(), new TypeReference<>() {});
        List<ApplicationFileDto> appFilePaths = objectMapper.readValue(mobileApp.getApplicationFile(), new TypeReference<>() {});
        List<VersioningAppDto> versioningApp = objectMapper.readValue(mobileApp.getVersioningApplication(), new TypeReference<>() {});
        List<ApplicationFileDto> docs = objectMapper.readValue(mobileApp.getDocumentation(), new TypeReference<>() {});

        return MobileAppResponseDto.builder()
                .applicationSourceFrontEnd(mobileApp.getApplicationSourceFrontEnd())
                .applicationSourceBackEnd(mobileApp.getApplicationSourceBackEnd())
                .databaseIp(mobileApp.getDatabaseIp())
                .createdAt(mobileApp.getCreatedAt())
                .updatedAt(mobileApp.getUpdatedAt())
                .sdaBackEnds(mobileApp.getBackEnds())
                .sdaFrontEnds(mobileApp.getFrontEnds())
                .webServer(mobileApp.getWebServer())
                .applicationApiList(appApiList)
                .applicationDatabaseList(dbList)
                .applicationFunction(mobileApp.getApplicationFunction())
                .applicationName(mobileApp.getApplicationName())
                .applicationUrl(applicationUrl)
                .role(roles)
                .description(mobileApp.getDescription())
                .mappingFunctions(mobileApp.getMappingFunctions())
                .picDevelopers(mobileApp.getPicDevelopers())
                .status(mobileApp.getStatus())
                .id(mobileApp.getId())
                .applicationFilePath(appFilePaths)
                .versioningApplication(versioningApp)
                .assetNumber(mobileApp.getAssetNumber())
                .sdaHostingList(mobileApp.getSdaHostingList())
                .businessImpactPriority(mobileApp.getBusinessImpactPriority())
                .documentation(docs)
                .sapIntegration(mobileApp.getSapIntegration())
                .appCategory(mobileApp.getAppCategory())
                .build();
    }

    @Transactional
    public MobileAppResponseDto createMobileApp(MobileAppDto request) throws Exception {
        Boolean existsByApplicationName = mobileAppRepository.existsByApplicationName(request.getApplicationName());
        if (existsByApplicationName) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Application Name already exists");
        }

        List<MappingFunctionEntity> mappingFunction = mappingFunctionRepository.findByMappingFunctionIsIn(request.getMappingFunction());
        if (mappingFunction.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mapping Function not found");
        }

        List<PICDeveloperEntity> picDeveloper = picDeveloperRepository.findByPersonalNameIsIn(request.getPicDeveloper());
        if (picDeveloper.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PIC Developer not found");
        }

        List<SDAHostingEntity> sdaHosting = sdaHostingRepository.findBySdaHostingIsIn(List.of(request.getSdaHosting()));
        String[] hostingName = null;
        if (Objects.nonNull(sdaHosting) && !sdaHosting.isEmpty()) {
            hostingName = sdaHosting.stream().map(SDAHostingEntity::getSdaHosting).toList().toArray(new String[0]);
        }
        request.setSdaHosting(hostingName);

        List<FrontEndEntity> frontEndData = frontEndRepository.findByFrontEndIsIn(request.getSdaFrontEnd());
        if (frontEndData.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Front End not found");
        }

        List<BackEndEntity> backendData = backEndRepository.findByBackEndIsIn(request.getSdaBackEnd());
        if (backendData.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Back End not found");
        }

        // Documentation
        List<ApplicationFileDto> documents = uploadDocument(request.getDocumentation());

        // App file
        List<ApplicationFileDto> ipa = uploadFileApp(request.getIpaFile(), "ipa");
        List<ApplicationFileDto> apk = uploadFileApp(request.getAndroidFile(), "apk");

        List<ApplicationFileDto> filePaths = new ArrayList<>();
        filePaths.addAll(ipa);
        filePaths.addAll(apk);

        MobileAppEntity mobileApp = new MobileAppEntity();
        MobileAppEntity payload = mobileAppPayload(request, mobileApp, documents, filePaths);
        payload.setAssetNumber(GenerateAssetNumber.generateAssetNumber("AM", mobileAppRepository.count() + 1)); // AM for 'Asset Mobile'
        payload.setMappingFunctions(mappingFunction);
        payload.setPicDevelopers(picDeveloper);
        payload.setFrontEnds(frontEndData);
        payload.setBackEnds(backendData);
        payload.setSdaHostingList(sdaHosting);
        mobileAppRepository.save(payload);

        return toMobileAppResponse(payload);
    }

    @Transactional(readOnly = true)
    public PaginationUtil<MobileAppEntity, MobileAppEntity> getAllMobileApp(Integer page, Integer perPage, UserFilterRequest filterRequest) {
        Specification<MobileAppEntity> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(filterRequest.getSearchTerm())) {
                predicates.add(
                        builder.or(
                                builder.like(builder.upper(root.get("applicationName")), "%" + filterRequest.getSearchTerm().toUpperCase() + "%"),
                                builder.like(builder.upper(root.get("assetNumber")), "%" + filterRequest.getSearchTerm().toUpperCase() + "%")
                        )
                );
            }

            if (Objects.nonNull(filterRequest.getFilterByStatus()) && !filterRequest.getFilterByStatus().isEmpty()) {
                predicates.add(
                        builder.in(root.get("status")).value(filterRequest.getFilterByStatus())
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        PageRequest pageRequest = PageRequest.of(page - 1, perPage, Sort.by(Sort.Order.desc("createdAt")));
        Page<MobileAppEntity> mobileApp = mobileAppRepository.findAll(specification, pageRequest);

        return new PaginationUtil<>(mobileApp, MobileAppEntity.class);
    }

    public MobileAppResponseDto getMobileAppById(Long id) throws Exception {
        MobileAppEntity mobileApp = mobileAppRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        return toMobileAppResponse(mobileApp);
    }

    @Transactional
    public MobileAppResponseDto updateMobileApp(Long id, MobileAppDto request) throws Exception {
        MobileAppEntity mobileApp = mobileAppRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        List<MappingFunctionEntity> mappingFunction = mappingFunctionRepository.findByMappingFunctionIsIn(request.getMappingFunction());
        if (mappingFunction.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mapping Function not found");
        }

        List<PICDeveloperEntity> picDeveloper = picDeveloperRepository.findByPersonalNameIsIn(request.getPicDeveloper());
        if (picDeveloper.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PIC Developer not found");
        }

        List<SDAHostingEntity> sdaHosting = sdaHostingRepository.findBySdaHostingIsIn(List.of(request.getSdaHosting()));

        List<FrontEndEntity> frontEndData = frontEndRepository.findByFrontEndIsIn(request.getSdaFrontEnd());
        if (frontEndData.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Front End not found");
        }

        List<BackEndEntity> backendData = backEndRepository.findByBackEndIsIn(request.getSdaBackEnd());
        if (backendData.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Back End not found");
        }

        // Get old documents and remove
        List<ApplicationFileDto> docs = objectMapper.readValue(mobileApp.getDocumentation(), new TypeReference<ArrayList<ApplicationFileDto>>() {});
        List<String> docPathList = docs.stream().map(ApplicationFileDto::getPath).toList();
        List<String> oldDocsName = docs.stream().map(ApplicationFileDto::getFilename).toList();

        boolean isNewDocNameAndOldDocNameEqual = request.getDocumentation() != null && Objects.equals(request.getDocumentation().stream().map(MultipartFile::getOriginalFilename).toList(), oldDocsName);

        if (!docPathList.isEmpty() && !isNewDocNameAndOldDocNameEqual) {
            storageService.deleteAllFileS3(docPathList);
        }

        // Upload new Documentation
        List<ApplicationFileDto> documentPaths = isNewDocNameAndOldDocNameEqual ? docs : new ArrayList<>();

        if (!isNewDocNameAndOldDocNameEqual) {
            documentPaths = uploadDocument(request.getDocumentation());
        }

        // Get Old files from DB and remove
        List<ApplicationFileDto> appFiles = objectMapper.readValue(mobileApp.getApplicationFile(), new TypeReference<ArrayList<ApplicationFileDto>>() {});
        List<String> oldAppFilePaths = appFiles.stream().map(ApplicationFileDto::getPath).toList();
        List<String> oldFilesName = appFiles.stream().map(ApplicationFileDto::getFilename).toList();

        if (!oldAppFilePaths.isEmpty()) {
            storageService.deleteAllFileS3(oldAppFilePaths);
        }

        // Upload new ipa file
        List<ApplicationFileDto> ipa = new ArrayList<>();
        if (request.getIpaFile() != null && !Objects.equals(request.getIpaFile().getOriginalFilename(), oldFilesName.toString())) {
            ipa = uploadFileApp(request.getIpaFile(), "ipa");
        }

        // Upload new apk file
        List<ApplicationFileDto> apk = new ArrayList<>();
        if (request.getAndroidFile() != null && !Objects.equals(request.getAndroidFile().getOriginalFilename(), oldFilesName.toString())) {
            apk = uploadFileApp(request.getAndroidFile(), "apk");
        }

        List<ApplicationFileDto> filePaths = new ArrayList<>();
        filePaths.addAll(ipa);
        filePaths.addAll(apk);

        MobileAppEntity payload = mobileAppPayload(request, mobileApp, documentPaths, filePaths);
        payload.setMappingFunctions(mappingFunction);
        payload.setPicDevelopers(picDeveloper);
        payload.setFrontEnds(frontEndData);
        payload.setBackEnds(backendData);
        payload.setSdaHostingList(sdaHosting);
        mobileAppRepository.saveAndFlush(payload);

        return toMobileAppResponse(payload);
    }

    @Transactional
    public void deleteById(Long id) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        MobileAppEntity data = mobileAppRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));

        // Remove old documents
        List<ApplicationFileDto> docs = objectMapper.readValue(data.getDocumentation(), new TypeReference<ArrayList<ApplicationFileDto>>() {});
        List<String> docList = docs.stream().map(ApplicationFileDto::getFilename).toList();
        if (!docList.isEmpty()) {
            storageService.deleteAllFileS3(docList);
        }

        // Remove old files
        List<ApplicationFileDto> appFiles = objectMapper.readValue(data.getApplicationFile(), new TypeReference<ArrayList<ApplicationFileDto>>() {});
        List<String> appFilePath = appFiles.stream().map(ApplicationFileDto::getPath).toList();
        if (!appFilePath.isEmpty()) {
            storageService.deleteAllFileS3(appFilePath);
        }

        mobileAppRepository.deleteById(id);
    }

    private MobileAppEntity mobileAppPayload(MobileAppDto request, MobileAppEntity mobileApp, List<ApplicationFileDto> documents, List<ApplicationFileDto> appFiles) throws JsonProcessingException {
        mobileApp.setApplicationName(request.getApplicationName());
        mobileApp.setStatus(request.getStatus());
        mobileApp.setRole(objectMapper.writeValueAsString(request.getRole()));
        mobileApp.setBusinessImpactPriority(request.getBusinessImpactPriority());
        mobileApp.setDescription(request.getDescription());
        mobileApp.setApplicationFunction(request.getApplicationFunction());
        mobileApp.setDocumentation(objectMapper.writeValueAsString(documents));
        mobileApp.setVersioningApplication(objectMapper.writeValueAsString(request.getVersioningApplication()));
        mobileApp.setApplicationUrl(objectMapper.writeValueAsString(request.getApplicationUrl()));
        mobileApp.setApplicationFile(objectMapper.writeValueAsString(appFiles));
        mobileApp.setWebServer(request.getWebServer());
        mobileApp.setSapIntegration(request.getSapIntegration());
        mobileApp.setApplicationSourceFrontEnd(request.getApplicationSourceFrontEnd());
        mobileApp.setApplicationSourceBackEnd(request.getApplicationSourceBackEnd());
        mobileApp.setDatabaseIp(request.getDatabaseIp());
        mobileApp.setApplicationApiList(objectMapper.writeValueAsString(request.getApplicationApiList()));
        mobileApp.setApplicationDatabaseList(objectMapper.writeValueAsString(request.getApplicationDatabaseList()));
        return mobileApp;
    }

    private List<ApplicationFileDto> uploadDocument(List<MultipartFile> documents) {
        if (documents == null || documents.isEmpty()) return Collections.emptyList();

        List<ApplicationFileDto> documentPaths = new ArrayList<>();
        String generatedString = generateRandomString();

        documents.forEach(doc -> {
            if (!Objects.equals(doc.getContentType(), "application/pdf")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Document must be pdf file");
            }

            try {
                String docFilename = time + generatedString + "_" + Objects.requireNonNull(doc.getOriginalFilename()).replace(" ", "_");

                String filepath = LocalDate.now().getYear() + "/docs/" + docFilename;
                ObjectWriteResponse objectWriteResponse = storageService.storeToS3(filepath, doc);

                ApplicationFileDto applicationFileDto = new ApplicationFileDto();
                applicationFileDto.setPath(objectWriteResponse.object());
                applicationFileDto.setSize(doc.getSize());
                applicationFileDto.setFilename(doc.getOriginalFilename());
                applicationFileDto.setMimeType(doc.getContentType());
                documentPaths.add(applicationFileDto);

            } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        });

        return documentPaths;
    }

    private List<ApplicationFileDto> uploadFileApp(MultipartFile file, String appFileCategory) throws Exception {
        if (file == null || file.isEmpty()) return Collections.emptyList();

        List<ApplicationFileDto> appFiles = new ArrayList<>();
        String generatedString = generateRandomString();
        ApplicationFileDto applicationFileDto = new ApplicationFileDto();

        if (Objects.equals(appFileCategory, "apk")) {
            if (!Objects.equals(file.getContentType(), "application/vnd.android.package-archive")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid android file");
            }

            String androidFilename = time + generatedString + "_" + Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");

            String filePath = LocalDate.now().getYear() + "/android/" + androidFilename;
            ObjectWriteResponse objectWriteResponse = storageService.storeToS3(filePath, file);

            applicationFileDto.setSize(file.getSize());
            applicationFileDto.setFilename(file.getOriginalFilename());
            applicationFileDto.setMimeType(file.getContentType());
            applicationFileDto.setPath(objectWriteResponse.object());
            appFiles.add(applicationFileDto);
        }

        if (Objects.equals(appFileCategory, "ipa")) {
            if (!Objects.equals(file.getContentType(), "application/octet-stream")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ipa file");

            String ipaFilename = time + generatedString + "_" + Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");
            String filePath = LocalDate.now().getYear() + "/ipa/" + ipaFilename;
            ObjectWriteResponse objectWriteResponse = storageService.storeToS3(filePath, file);

            applicationFileDto.setSize(file.getSize());
            applicationFileDto.setFilename(file.getOriginalFilename());
            applicationFileDto.setMimeType(file.getContentType());
            applicationFileDto.setPath(objectWriteResponse.object());
            appFiles.add(applicationFileDto);
        }

        return appFiles;
    }

    private String generateRandomString() {
        Random random = new Random();
        return random.ints(97, 122 + 1)
                .limit(11)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}