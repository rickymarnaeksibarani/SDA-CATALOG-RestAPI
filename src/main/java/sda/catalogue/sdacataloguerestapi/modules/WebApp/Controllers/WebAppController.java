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
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createWebApp(
            @Valid @ModelAttribute WebAppPostDTO request,
            @RequestPart("picDeveloperList") List<Long> picDeveloperList,
//            @RequestPart("picAnalystList") List<Long> picAnalystList,
            @RequestPart("mappingFunctionList") List<Long> mappingFunctionList,
            @RequestPart("frontEndList") List<Long> frontEndList,
            @RequestPart("backEndList") List<Long> backEndList,
            @RequestPart("webServerList") List<Long> webServerList,
            @RequestPart("versioningApplicationList") List<VersioningApplicationDTO> versioningApplicationList,
            @RequestPart("databaseList") List<DatabaseDTO> databaseList,
            @RequestPart("apiList") List<ApiDTO> apiList
    ) {
        try {
            WebAppEntity result = webAppService.createWebApp(request, picDeveloperList, mappingFunctionList, frontEndList, backEndList, webServerList, versioningApplicationList, databaseList, apiList);
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
    ) throws JsonProcessingException {
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

    //Update Data Web App By UUID
    @PutMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateWebApp(
            @PathVariable("uuid") UUID uuid,
            @Valid @ModelAttribute WebAppPostDTO request,
            @RequestPart("picDeveloperList") List<Long> picDeveloperList,
//            @RequestPart("picAnalystList") List<Long> picAnalystList,
            @RequestPart("mappingFunctionList") List<Long> mappingFunctionList,
            @RequestPart("frontEndList") List<Long> frontEndList,
            @RequestPart("backEndList") List<Long> backEndList,
            @RequestPart("webServerList") List<Long> webServerList,
            @RequestPart("versioningApplicationList") List<VersioningApplicationEntity> versioningApplicationEntityList,
            @RequestPart("databaseList") List<DatabaseEntity> databaseEntityList,
            @RequestPart("apiList") List<ApiEntity> apiList

            ) {
        try {
            WebAppEntity result = webAppService.updateWebAppByUuid(uuid, request, picDeveloperList, mappingFunctionList, frontEndList, backEndList, webServerList, versioningApplicationEntityList, databaseEntityList, apiList);
            ApiResponse<WebAppEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Successfully updated data webapp!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @DeleteMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteWebAppByUuid(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            webAppService.deleteWebAppByUuid(uuid);
            ApiResponse<WebAppEntity> response = new ApiResponse<>(HttpStatus.OK, "Success delete data web app!", null);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

}

