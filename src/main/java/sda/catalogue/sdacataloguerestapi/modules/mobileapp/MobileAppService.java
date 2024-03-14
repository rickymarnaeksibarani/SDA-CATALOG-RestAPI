package sda.catalogue.sdacataloguerestapi.modules.mobileapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto.*;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.entity.MobileAppEntity;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.repository.MobileAppRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Service
public class MobileAppService {
    @Autowired
    private MobileAppRepository mobileAppRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final Path uploadpath = Paths.get("src/main/resources/uploads/");
    private final Date date = new Date();
    private final Long time = date.getTime();

    private MobileAppResponseDto toMobileAppResponse(MobileAppEntity mobileApp) throws Exception {
        List<AppApiListDto> appApiList = objectMapper.readValue(mobileApp.getApplicationApiList(), new TypeReference<>() {});
        List<DbListDto> dbList = objectMapper.readValue(mobileApp.getApplicationDatabaseList(), new TypeReference<>() {});
        ApplicationUrlDto applicationUrl = objectMapper.readValue(mobileApp.getApplicationUrl(), new TypeReference<>() {});
        List<Role> roles = objectMapper.readValue(mobileApp.getRole(), new TypeReference<>() {});
        String[] departments = objectMapper.readValue(mobileApp.getDepartment(), new TypeReference<>() {});
        String[] mappingFunctions = objectMapper.readValue(mobileApp.getMappingFunction(), new TypeReference<>() {});
        String[] picDevelopers = objectMapper.readValue(mobileApp.getPicDeveloper(), new TypeReference<>() {});
        List<String> appFilePaths = objectMapper.readValue(mobileApp.getApplicationFile(), new TypeReference<>() {});
        List<VersioningAppDto> versioningApp = objectMapper.readValue(mobileApp.getVersioningApplication(), new TypeReference<>() {});
        List<String> sdaHostings = objectMapper.readValue(mobileApp.getSdaHosting(), new TypeReference<>() {});
        List<String> docs = objectMapper.readValue(mobileApp.getDocumentation(), new TypeReference<>() {});
        List<String> sdaBackend = objectMapper.readValue(mobileApp.getSdaBackEnd(), new TypeReference<>() {});
        List<String> sdaFrontend = objectMapper.readValue(mobileApp.getSdaFrontEnd(), new TypeReference<>() {});

        return MobileAppResponseDto.builder()
                .applicationSourceFrontEnd(mobileApp.getApplicationSourceFrontEnd())
                .applicationSourceBackEnd(mobileApp.getApplicationSourceBackEnd())
                .databaseIp(mobileApp.getDatabaseIp())
                .createdAt(mobileApp.getCreatedAt())
                .updatedAt(mobileApp.getUpdatedAt())
                .sdaBackEnd(sdaBackend)
                .sdaFrontEnd(sdaFrontend)
                .webServer(mobileApp.getWebServer())
                .applicationApiList(appApiList)
                .applicationDatabaseList(dbList)
                .applicationFunction(mobileApp.getApplicationFunction())
                .applicationName(mobileApp.getApplicationName())
                .applicationUrl(applicationUrl)
                .role(roles)
                .description(mobileApp.getDescription())
                .department(departments)
                .mappingFunction(mappingFunctions)
                .picDeveloper(picDevelopers)
                .status(mobileApp.getStatus())
                .id(mobileApp.getId())
                .applicationFilePath(appFilePaths)
                .versioningApplication(versioningApp)
                .pmoNumber(mobileApp.getPmoNumber())
                .sdaHosting(sdaHostings)
                .businessImpactPriority(mobileApp.getBusinessImpactPriority())
                .documentation(docs)
                .sapIntegration(mobileApp.getSapIntegration())
                .build();
    }

    @Transactional
    public MobileAppResponseDto createMobileApp(MobileAppDto request) throws Exception {
        Boolean existsByApplicationName = mobileAppRepository.existsByApplicationName(request.getApplicationName());

        if (existsByApplicationName) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Application Name already exists");
        }

        // Documentation
        List<String> documentPaths = uploadDocument(request.getDocumentation());

        // App file
        String ipa = uploadFileApp(request.getIpaFile(), "ipa");
        String apk = uploadFileApp(request.getAndroidFile(), "apk");

        List<String> filePaths = new ArrayList<>();
        filePaths.add(ipa);
        filePaths.add(apk);

        MobileAppEntity mobileApp = new MobileAppEntity();
        MobileAppEntity payload = mobileAppPayload(request, mobileApp, documentPaths, filePaths);
        mobileAppRepository.save(payload);

        return toMobileAppResponse(payload);
    }

    @Transactional(readOnly = true)
    public PaginationUtil<MobileAppEntity, MobileAppEntity> getAllMobileApp(Integer page, Integer perPage, String queryParam) {
        PageRequest pageRequest = PageRequest.of(page - 1, perPage, Sort.by(Sort.Order.desc("createdAt")));

        Specification<MobileAppEntity> specification = null;
        if (queryParam != null || !queryParam.isEmpty()) {
            specification = Specification.where((root, query, criteriaBuilder) -> query.where(
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.upper(root.get("applicationName")), "%" + queryParam.toUpperCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.upper(root.get("pmoNumber")), "%" + queryParam.toUpperCase() + "%")
                    )
            ).getRestriction());
        }

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

        // Documentation
        List<String> documentPaths = uploadDocument(request.getDocumentation());

        // App file
        String ipa = uploadFileApp(request.getIpaFile(), "ipa");
        String apk = uploadFileApp(request.getAndroidFile(), "apk");

        List<String> filePaths = new ArrayList<>();
        filePaths.add(ipa);
        filePaths.add(apk);

        // Remove old documents
        List docList = objectMapper.readValue(mobileApp.getDocumentation(), List.class);
        if (Objects.nonNull(docList)) {
            docList.forEach(docPath -> {
                if (Objects.nonNull(docPath)) {
                    Path path = Paths.get((String) docPath);

                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        // Remove old files
        List appFilePath = objectMapper.readValue(mobileApp.getApplicationFile(), List.class);
        if (appFilePath != null) {
            appFilePath.forEach(path -> {
                if (Objects.nonNull(path)) {
                    Path filepath = Paths.get((String) path);

                    try {
                        Files.deleteIfExists(filepath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        MobileAppEntity payload = mobileAppPayload(request, mobileApp, documentPaths, filePaths);
        mobileAppRepository.saveAndFlush(payload);

        return toMobileAppResponse(payload);
    }

    @Transactional
    public void deleteById(Long id) throws JsonProcessingException {
        if (!mobileAppRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");
        }

        Optional<MobileAppEntity> data = mobileAppRepository.findById(id);

        // Remove old documents
        List docList = objectMapper.readValue(data.get().getDocumentation(), List.class);
        if (Objects.nonNull(docList)) {
            docList.forEach(docPath -> {
                if (Objects.nonNull(docPath)) {
                    Path path = Paths.get((String) docPath);

                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        // Remove old files
        List appFilePath = objectMapper.readValue(data.get().getApplicationFile(), List.class);
        if (appFilePath != null) {
            appFilePath.forEach(path -> {
                if (Objects.nonNull(path)) {
                    Path filepath = Paths.get((String) path);

                    try {
                        Files.deleteIfExists(filepath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        mobileAppRepository.deleteById(id);
    }

    private MobileAppEntity mobileAppPayload(MobileAppDto request, MobileAppEntity mobileApp, List<String> documentPaths, List<String> filePaths) throws JsonProcessingException {
        mobileApp.setApplicationName(request.getApplicationName());
        mobileApp.setPmoNumber(request.getPmoNumber());
        mobileApp.setStatus(request.getStatus());
        mobileApp.setSdaHosting(objectMapper.writeValueAsString(request.getSdaHosting()));
        mobileApp.setMappingFunction(objectMapper.writeValueAsString(request.getMappingFunction()));
        mobileApp.setDepartment(objectMapper.writeValueAsString(request.getDepartment()));
        mobileApp.setRole(objectMapper.writeValueAsString(request.getRole()));
        mobileApp.setBusinessImpactPriority(request.getBusinessImpactPriority());
        mobileApp.setPicDeveloper(objectMapper.writeValueAsString(request.getPicDeveloper()));
        mobileApp.setDescription(request.getDescription());
        mobileApp.setApplicationFunction(request.getApplicationFunction());
        mobileApp.setDocumentation(objectMapper.writeValueAsString(documentPaths));
        mobileApp.setVersioningApplication(objectMapper.writeValueAsString(request.getVersioningApplication()));
        mobileApp.setApplicationUrl(objectMapper.writeValueAsString(request.getApplicationUrl()));
        mobileApp.setApplicationFile(objectMapper.writeValueAsString(filePaths));
        mobileApp.setSdaFrontEnd(objectMapper.writeValueAsString(request.getSdaFrontEnd()));
        mobileApp.setSdaBackEnd(objectMapper.writeValueAsString(request.getSdaBackEnd()));
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
        if (documents == null || documents.isEmpty()) return null;

        List<String> documentPaths = new ArrayList<>();
        String generatedString = generateRandomString();

        documents.forEach(doc -> {
            try {
                String docFilename = time + generatedString + "_" + doc.getOriginalFilename();
                Path docFileDestination = Files.createDirectories(Path.of(uploadpath + "/document/"));
                doc.transferTo(Path.of(docFileDestination + "/" + docFilename));
                documentPaths.add(docFileDestination + "/" + docFilename);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return documentPaths;
    }

    private String uploadFileApp(MultipartFile file, String appFileCategory) throws IOException {
        if (file == null || file.isEmpty()) return null;

        String filePaths = null;
        String generatedString = generateRandomString();

        if (Objects.equals(appFileCategory, "apk")) {
            String androidFilename = time + generatedString + "_" + file.getOriginalFilename();
            Path fileDestination = Files.createDirectories(Path.of(uploadpath + "/apk/"));
            Path resolve = fileDestination.resolve(androidFilename.trim());
            Files.copy(file.getInputStream(), resolve);
            filePaths = String.valueOf(resolve);
        }

        if (Objects.equals(appFileCategory, "ipa")) {
            String ipaFilename = time + generatedString + "_" + file.getOriginalFilename();

            Path fileDestination = Files.createDirectories(Path.of(uploadpath + "/ipa/"));
            Path resolve = fileDestination.resolve(ipaFilename.trim());
            Files.copy(file.getInputStream(), resolve);
            filePaths = String.valueOf(resolve);
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
