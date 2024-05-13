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
        List<String> appFilePaths = objectMapper.readValue(mobileApp.getApplicationFile(), new TypeReference<>() {});
        List<VersioningAppDto> versioningApp = objectMapper.readValue(mobileApp.getVersioningApplication(), new TypeReference<>() {});
        List<String> docs = objectMapper.readValue(mobileApp.getDocumentation(), new TypeReference<>() {});

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
                .pmoNumber(mobileApp.getPmoNumber())
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
        List<String> documentPaths = uploadDocument(request.getDocumentation());

        // App file
        List<String> ipa = uploadFileApp(request.getIpaFile(), "ipa");
        List<String> apk = uploadFileApp(request.getAndroidFile(), "apk");

        List<String> filePaths = new ArrayList<>();
        filePaths.addAll(ipa);
        filePaths.addAll(apk);

        MobileAppEntity mobileApp = new MobileAppEntity();
        MobileAppEntity payload = mobileAppPayload(request, mobileApp, documentPaths, filePaths);
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
                                builder.like(builder.upper(root.get("pmoNumber")), "%" + filterRequest.getSearchTerm().toUpperCase() + "%")
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

        // Upload new Documentation
        List<String> documentPaths = uploadDocument(request.getDocumentation());

        // Get old documents and remove
        List<String> docList = objectMapper.readValue(mobileApp.getDocumentation(), new TypeReference<>() {});
        if (Objects.nonNull(docList) && !docList.isEmpty()) {
            storageService.deleteAllFileS3(docList);
        }

        // Upload new App file
        List<String> ipa = uploadFileApp(request.getIpaFile(), "ipa");
        List<String> apk = uploadFileApp(request.getAndroidFile(), "apk");

        List<String> filePaths = new ArrayList<>();
        filePaths.addAll(ipa);
        filePaths.addAll(apk);

        // Get Old files from DB and remove
        List<String> appFilePath = objectMapper.readValue(mobileApp.getApplicationFile(), new TypeReference<>() {});
        if (appFilePath != null && !appFilePath.isEmpty()) {
            storageService.deleteAllFileS3(appFilePath);
        }

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
        if (!mobileAppRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");
        }

        Optional<MobileAppEntity> data = mobileAppRepository.findById(id);

        // Remove old documents
        List<String> docList = objectMapper.readValue(data.get().getDocumentation(), new TypeReference<>() {});
        if (Objects.nonNull(docList) && !docList.isEmpty()) {
            storageService.deleteAllFileS3(docList);
        }

        // Remove old files
        List<String> appFilePath = objectMapper.readValue(data.get().getApplicationFile(), new TypeReference<>() {});
        if (appFilePath != null && !appFilePath.isEmpty()) {
            storageService.deleteAllFileS3(appFilePath);
        }

        mobileAppRepository.deleteById(id);
    }

    private MobileAppEntity mobileAppPayload(MobileAppDto request, MobileAppEntity mobileApp, List<String> documentPaths, List<String> filePaths) throws JsonProcessingException {
        mobileApp.setApplicationName(request.getApplicationName());
        mobileApp.setPmoNumber(request.getPmoNumber());
        mobileApp.setStatus(request.getStatus());
        mobileApp.setRole(objectMapper.writeValueAsString(request.getRole()));
        mobileApp.setBusinessImpactPriority(request.getBusinessImpactPriority());
        mobileApp.setDescription(request.getDescription());
        mobileApp.setApplicationFunction(request.getApplicationFunction());
        mobileApp.setDocumentation(objectMapper.writeValueAsString(documentPaths));
        mobileApp.setVersioningApplication(objectMapper.writeValueAsString(request.getVersioningApplication()));
        mobileApp.setApplicationUrl(objectMapper.writeValueAsString(request.getApplicationUrl()));
        mobileApp.setApplicationFile(objectMapper.writeValueAsString(filePaths));
        mobileApp.setWebServer(request.getWebServer());
        mobileApp.setSapIntegration(request.getSapIntegration());
        mobileApp.setApplicationSourceFrontEnd(request.getApplicationSourceFrontEnd());
        mobileApp.setApplicationSourceBackEnd(request.getApplicationSourceBackEnd());
        mobileApp.setDatabaseIp(request.getDatabaseIp());
        mobileApp.setApplicationApiList(objectMapper.writeValueAsString(request.getApplicationApiList()));
        mobileApp.setApplicationDatabaseList(objectMapper.writeValueAsString(request.getApplicationDatabaseList()));
        return mobileApp;
    }

    private List<String> uploadDocument(List<MultipartFile> documents) {
        if (documents == null || documents.isEmpty()) return Collections.emptyList();

        List<String> documentPaths = new ArrayList<>();
        String generatedString = generateRandomString();

        documents.forEach(doc -> {
            if (!Objects.equals(doc.getContentType(), "application/pdf")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Document must be pdf file");
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

    private List<String> uploadFileApp(MultipartFile file, String appFileCategory) throws Exception {
        if (file == null || file.isEmpty()) return Collections.emptyList();

        List<String> filePaths = new ArrayList<>();
        String generatedString = generateRandomString();

        if (Objects.equals(appFileCategory, "apk")) {
            if (!Objects.equals(file.getContentType(), "application/vnd.android.package-archive")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid android file");
            }

            String androidFilename = time + generatedString + "_" + Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");

            String filePath = LocalDate.now().getYear() + "/android/" + androidFilename;
            ObjectWriteResponse objectWriteResponse = storageService.storeToS3(filePath, file);
            filePaths.add(objectWriteResponse.object());
        }

        if (Objects.equals(appFileCategory, "ipa")) {
            if (!Objects.equals(file.getContentType(), "application/octet-stream")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ipa file");

            String ipaFilename = time + generatedString + "_" + Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");
            String filePath = LocalDate.now().getYear() + "/ipa/" + ipaFilename;
            ObjectWriteResponse objectWriteResponse = storageService.storeToS3(filePath, file);
            filePaths.add(objectWriteResponse.object());
        }

        return filePaths;
    }

    private String generateRandomString() {
        Random random = new Random();
        return random.ints(97, 122 + 1)
                .limit(11)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}