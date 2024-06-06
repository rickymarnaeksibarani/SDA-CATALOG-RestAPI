package sda.catalogue.sdacataloguerestapi.modules.WebServer.Controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.WebAppRequestDto;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Dto.WebServerDTO;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Dto.WebServerRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Entities.WebServerEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Services.WebServerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/web-server")
//@CrossOrigin(origins = "${spring.frontend}")
public class WebServerController {
    @Autowired
    private WebServerService webServerService;

    //Getting data Web Server with search and pagination
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchWebServer(WebServerRequestDTO searchDTO) {

        try {
            PaginationUtil<WebServerEntity, WebServerEntity> result = webServerService.getAllWebServerByPagination(searchDTO);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data Web Server!", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @GetMapping(value = "/{id_web_server}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getWebServerById(
            @PathVariable("id_web_server")Long id_web_server
    ) {
        try {
            WebServerEntity result = webServerService.getWebServerById(id_web_server);
            ApiResponse<WebServerEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrieved data web server!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createWebServer(
            @Valid @RequestBody WebServerDTO request
    ) {
        try {
            WebServerEntity result = webServerService.createWebServer(request);
            ApiResponse<WebServerEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success create data web server!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PutMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<WebServerEntity> updateWebServer(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody WebServerDTO request
    ) {
            WebServerEntity result = webServerService.updateWebServer(uuid, request);
            return ApiResponse.<WebServerEntity>builder()
                    .result(result)
                    .status(HttpStatus.OK)
                    .message("Success update data web server!")
                    .build();
    }

    @DeleteMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> deleteBackEnd(
            @PathVariable("uuid") UUID uuid
    ) {
        webServerService.deleteWebServer(uuid);
        return ApiResponse.<String>builder()
                .message("Success delete data web server!")
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
