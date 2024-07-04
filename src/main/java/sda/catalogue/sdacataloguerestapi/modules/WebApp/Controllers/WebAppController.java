package sda.catalogue.sdacataloguerestapi.modules.WebApp.Controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.*;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.ApiEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.DatabaseEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.VersioningApplicationEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Services.WebAppService;

import javax.validation.Valid;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@Validated
@RequestMapping("/api/v1/web-app")
//@CrossOrigin(origins = "${spring.frontend}")
public class WebAppController {
    @Autowired
    WebAppService webAppService;

    //Create Data Web App
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createWebApp(
            @Valid @RequestPart WebAppPostDTO request,
            @RequestPart("picDeveloper") List<Long> picDeveloper,
            @RequestPart("picAnalyst") List<Long> picAnalyst,
            @RequestPart("mappingFunction") List<Long> mappingFunction,
            @RequestPart("frontEnd") List<Long> frontEnd,
            @RequestPart("backEnd") List<Long> backEnd,
            @RequestPart("webServer") List<Long> webServer
    ) {
        try {
            WebAppEntity result = webAppService.createWebApp(request, picDeveloper, picAnalyst,mappingFunction, frontEnd, backEnd, webServer);
            ApiResponse<WebAppEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Successfully created data webapp!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Getting Data Web App by Pagination
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchWebApp(WebAppRequestDto searchDTO) {
        try {
            PaginationUtil<WebAppEntity, WebAppEntity> result = webAppService.getAllWebApp(searchDTO);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success get data", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Getting Data Web App By ID
    @GetMapping(value = "/{id_webapp}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getWebAppById(
            @PathVariable("id_webapp")Long id_webapp
    ) {
        try {
            WebAppEntity result = webAppService.getWebAppById(id_webapp);
            ApiResponse<WebAppEntity> response = new ApiResponse<>(HttpStatus.OK, "Successfully retrieved data webapp!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @GetMapping(value = "/stats-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStatsStatus() {
        try {
            SDAStatusStatsDTO result = webAppService.statsWebByStatus();
            ApiResponse<SDAStatusStatsDTO> response = new ApiResponse<>(HttpStatus.OK, "Successfully retrieved statistic by status!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @GetMapping(value = "/stats-sda-hosting", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStatsSdaHosting(
            @RequestParam(name = "dataType", defaultValue = "array") String dataType
    ) {
        try {
            if (Objects.equals(dataType, "object")) {
                Map<String, Long> result = webAppService.statsSdaHostingObject();
                ApiResponse<Map<String, Long>> response = new ApiResponse<>(HttpStatus.OK, "Successfully retrieved statistic by sda hosting!", result);
                return new ResponseEntity<>(response, response.getStatus());
            } else {
                List<SDAHostingStatsDTO> result = webAppService.statsSdaHostingArray();
                ApiResponse<List<SDAHostingStatsDTO>> response = new ApiResponse<>(HttpStatus.OK, "Successfully retrieved statistic by sda hosting!", result);
                return new ResponseEntity<>(response, response.getStatus());
            }
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Update Data Web App By Id
    @PutMapping(value = "/{idWebApp}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateWebApp(
            @PathVariable("idWebApp") Long idWebApp,
            @Valid @RequestPart("request") WebAppPostDTO request,
            @RequestParam("picDeveloper") List<Long> picDeveloper,
            @RequestParam("picAnalyst") List<Long> picAnalyst,
            @RequestParam("mappingFunction") List<Long> mappingFunction,
            @RequestParam("frontEnd") List<Long> frontEnd,
            @RequestParam("backEnd") List<Long> backEnd,
            @RequestParam("webServer") List<Long> webServer
    ) {
        log.info("request {}", request);
        try {
            WebAppEntity result = webAppService.updateWebAppById(idWebApp, request, picDeveloper, picAnalyst, mappingFunction, frontEnd, backEnd, webServer);
            ApiResponse<WebAppEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Successfully updated data webapp!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @DeleteMapping(value = "/{idWebApp}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteWebAppByUuid(
            @PathVariable("idWebApp") Long idWebApp
    ) {
        try {
            webAppService.deleteWebAppById(idWebApp);
            ApiResponse<WebAppEntity> response = new ApiResponse<>(HttpStatus.OK, "Success delete data web app!", null);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

}

