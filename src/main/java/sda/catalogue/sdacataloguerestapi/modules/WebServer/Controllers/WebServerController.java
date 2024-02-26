package sda.catalogue.sdacataloguerestapi.modules.WebServer.Controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Dto.WebServerDTO;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Entities.WebServerEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Services.WebServerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/web-server")
@CrossOrigin(origins = "${spring.frontend}")
public class WebServerController {
    @Autowired
    private WebServerService webServerService;

    //Getting data Web Server with search and pagination
    @GetMapping()
    public ResponseEntity<?> searchWebServer(@ModelAttribute WebServerDTO searchDTO,
                                             @RequestParam("page")String page,
                                             @RequestParam("size")String size) {
        try {
            PaginationUtil<WebServerEntity, WebServerDTO> result = webServerService.getAllWebServerByPagination(Integer.parseInt(page), Integer.parseInt(size));
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data web server!", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getBackendByUuid(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            WebServerEntity result = webServerService.getWebServerByUuid(uuid);
            ApiResponse<WebServerEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrieved data web server!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createBackEnd(
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

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateBackEnd(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody WebServerDTO request
    ) {
        try {
            WebServerEntity result = webServerService.updateWebServer(uuid, request);
            ApiResponse<WebServerEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success update data web server!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteBackEnd(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            WebServerEntity result = webServerService.deleteWebServer(uuid);
            ApiResponse<WebServerEntity> response = new ApiResponse<>(HttpStatus.OK, "Success delete data web server!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
