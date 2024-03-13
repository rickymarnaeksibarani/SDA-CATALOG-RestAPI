package sda.catalogue.sdacataloguerestapi.modules.mobileapp;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Services.DocumentUploadService;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto.MobileAppDto;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto.MobileAppResponseDto;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.entity.MobileAppEntity;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.repository.MobileAppRepository;

import java.io.IOException;
import java.net.URI;
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
    private DocumentUploadService documentUploadService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String uploadDir = "src/main/resources/uploads/";
    private final Path uploadpath = Paths.get("src/main/resources/uploads/");
    private final Date date = new Date();
    private final Long time = date.getTime();

    @Transactional
    public MobileAppEntity createMobileApp(MobileAppDto request) throws Exception {
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

        return mobileAppRepository.save(payload);
    }

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

    public MobileAppEntity getMobileAppById(Long id) {
        return mobileAppRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
    }

    @Transactional
    public MobileAppEntity updateMobileApp(Long id, MobileAppDto request) throws Exception {
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
        docList.forEach(docPath -> {
            Path path = Paths.get((String) docPath);
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Remove old files
        List appFilePath = objectMapper.readValue(mobileApp.getApplicationFile(), List.class);
        appFilePath.forEach(path -> {
            Path filepath = Paths.get((String) path);
            try {
                Files.deleteIfExists(filepath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        MobileAppEntity payload = mobileAppPayload(request, mobileApp, documentPaths, filePaths);
        return mobileAppRepository.saveAndFlush(payload);
    }

    @Transactional
    public void deleteById(Long id) throws JsonProcessingException {
        if (!mobileAppRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");
        }

        Optional<MobileAppEntity> data = mobileAppRepository.findById(id);

        // Remove old documents
        List docList = objectMapper.readValue(data.get().getDocumentation(), List.class);
        docList.forEach(docPath -> {
            Path path = Paths.get((String) docPath);
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Remove old files
        List appFilePath = objectMapper.readValue(data.get().getApplicationFile(), List.class);
        appFilePath.forEach(path -> {
            Path filepath = Paths.get((String) path);
            try {
                Files.deleteIfExists(filepath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

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
