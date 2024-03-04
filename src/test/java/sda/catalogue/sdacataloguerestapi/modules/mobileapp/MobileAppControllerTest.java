package sda.catalogue.sdacataloguerestapi.modules.mobileapp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.enums.BusinessImpactPriority;
import sda.catalogue.sdacataloguerestapi.core.enums.Role;
import sda.catalogue.sdacataloguerestapi.core.enums.SapIntegration;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto.*;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.entity.MobileAppEntity;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.repository.MobileAppRepository;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class MobileAppControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MobileAppRepository mobileAppRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    void setUp() {
        mobileAppRepository.deleteAll();
    }

    @Test
    void createMobileAppSuccess() throws Exception {
        MockMultipartFile androidFile = new MockMultipartFile("androidFile", "RootExplorer.apk", "application/vnd.android.package-archive", getClass().getResourceAsStream("/file-test/RootExplorer.apk"));
        MockMultipartFile iosFile = new MockMultipartFile("ipaFile", "RootExplorer.apk", "application/vnd.android.package-archive", getClass().getResourceAsStream("/file-test/RootExplorer.apk"));
        MockMultipartFile documentation1 = new MockMultipartFile("documentation[0]", "doc-test.pdf", "application/pdf", getClass().getResourceAsStream("/file-test/doc-test.pdf"));
        MockMultipartFile documentation2 = new MockMultipartFile("documentation[1]", "doc-test.pdf", "application/pdf", getClass().getResourceAsStream("/file-test/doc-test.pdf"));

        mockMvc.perform(
                multipart("/api/v1/mobile-app")
                        .file(androidFile)
                        .file(iosFile)
                        .file(documentation1)
                        .file(documentation2)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("applicationName", "Top up Sob")
                        .param("pmoNumber", "PMO1337")
                        .param("status", String.valueOf(Status.ACTIVE))
                        .param("sdaHosting[0]", "AWS")
                        .param("sdaHosting[1]", "Ajure")
                        .param("mappingFunction[0]", "Line Main")
                        .param("mappingFunction[1]",  "Base Main")
                        .param("department[0]", "Line Main")
                        .param("department[1]", "Base Main")
                        .param("role[0]", String.valueOf(Role.Admin))
                        .param("role[1]", String.valueOf(Role.User))
                        .param("businessImpactPriority", String.valueOf(BusinessImpactPriority.Low))
                        .param("picDeveloper[0]", "Aing")
                        .param("picDeveloper[1]", "Maung")
                        .param("description", "Deskripsi aplikasi")
                        .param("applicationFunction", "Untuk hek nasa")
                        .param("versioningApplication[0].version", "1.0.0")
                        .param("versioningApplication[0].description", "Fitur Terbang")
                        .param("versioningApplication[0].releaseDate", "2024-01-22")
                        .param("versioningApplication[1].version", "1.2.0")
                        .param("versioningApplication[1].description", "Fitur Hack Nasa")
                        .param("versioningApplication[1].releaseDate", "2024-03-01")
                        .param("applicationUrl.appstoreUrl", "https://apstore.com")
                        .param("applicationUrl.playstoreUrl", "https://playstore.com")
                        .param("sdaFrontEnd[0]", "Angular").param("sdaFrontEnd[1]", "NextJs")
                        .param("sdaBackEnd[0]", "Spring Boot").param("sdaBackEnd[1]", "NestJs")
                        .param("webServer", "Nginx")
                        .param("sapIntegration", SapIntegration.Not_Integrated.name())
                        .param("applicationSourceFrontEnd", "192.168.1.1")
                        .param("applicationSourceBackEnd", "192.168.1.1")
                        .param("databaseIp", "192.168.1.1")
                        .param("applicationApiList[0].apiName", "top_up_sob")
                        .param("applicationApiList[0].ipApi", "192.168.1.1")
                        .param("applicationApiList[0].user", "user")
                        .param("applicationApiList[0].password", "password")
                        .param("applicationApiList[1].apiName", "payment_api")
                        .param("applicationApiList[1].ipApi", "192.168.1.1")
                        .param("applicationApiList[1].user", "user")
                        .param("applicationApiList[1].password", "password")
                        .param("applicationDatabaseList[0].dbName", "top_up_sob")
                        .param("applicationDatabaseList[0].ipDatabase", "192.168.1.1")
                        .param("applicationDatabaseList[0].type", "postgres")
                        .param("applicationDatabaseList[0].user", "top_up")
                        .param("applicationDatabaseList[0].password", "password")
                        .param("applicationDatabaseList[1].dbName", "payment_db")
                        .param("applicationDatabaseList[1].ipDatabase", "192.168.1.1")
                        .param("applicationDatabaseList[1].type", "postgres")
                        .param("applicationDatabaseList[1].user", "payment")
                        .param("applicationDatabaseList[1].password", "password")
        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            Object response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response);

            List<MobileAppEntity> mobileAppDb = mobileAppRepository.findAll();
            assertNotNull(mobileAppDb);
        });
    }
}