package sda.catalogue.sdacataloguerestapi.modules.WebApp.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sda.catalogue.sdacataloguerestapi.core.TangerangValidation.TangerangRequestException;
import sda.catalogue.sdacataloguerestapi.core.TangerangResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.TangerangResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.TangerangValidation.TangerangValidator;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.WebAppPostDTO;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Services.WebAppService;

import java.util.*;

@RestController
@Validated
@RequestMapping("/api/v1/web-app")
public class WebAppController {
    @Autowired
    WebAppService webAppService;

    //Getting Data Web App with search and pagination parameters
    @GetMapping()
    public ResponseEntity<?> searchWebApps(
            @RequestParam(name = "searchTerm", defaultValue = "") String searchTerm,
            @RequestParam(name = "page", defaultValue = "1") long page,
            @RequestParam(name = "size", defaultValue = "10") long size
    ) {
        try {
            PaginateResponse<List<WebAppEntity>> result = webAppService.searchAndPaginate(searchTerm, page, size);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Successfully retrieved data webapp!", result), HttpStatus.OK);
        } catch (TangerangRequestException error) {
            return error.GlobalTangerangRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Getting Data Web App By UUID
    @GetMapping("/{uuid}")
    public ResponseEntity<?> getWebAppByUuid(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            WebAppEntity result = webAppService.getWebAppByUuid(uuid);
            ApiResponse<WebAppEntity> response = new ApiResponse<>(HttpStatus.OK, "Successfully retrieved data webapp!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (TangerangRequestException error) {
            return error.GlobalTangerangRequestException(error.getMessage(), error.getStatus());
        }
    }


    //Create Data Web App
    @PostMapping()
    public ResponseEntity<?> createWebApp(
            @RequestBody WebAppPostDTO request
    ) {
        try {
//            ResponseEntity<?> validationResponse = TangerangValidator.TangerangValidator(webappData);
//            if (validationResponse != null) {
//                return validationResponse;
//            }
            WebAppEntity result = webAppService.createWebApp(request);
            ApiResponse<WebAppEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Successfully created data webapp!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (TangerangRequestException error) {
            return error.GlobalTangerangRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Update Data Web App By UUID
    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateWebApp(
            @PathVariable("uuid") UUID uuid,
            @RequestBody @Valid WebAppEntity request
    ) {
        try {
            WebAppEntity result = webAppService.updateWebAppByUuid(uuid, request);
            ApiResponse<WebAppEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Successfully updated data webapp!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (TangerangRequestException error) {
            return error.GlobalTangerangRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Delete Data Web App by UUID
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteWebAppByUuid(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            WebAppEntity result = webAppService.deleteWebAppByUuid(uuid);
            ApiResponse<WebAppEntity> response = new ApiResponse<>(HttpStatus.OK, "Successfully deleted data webapp!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (TangerangRequestException error) {
            return error.GlobalTangerangRequestException(error.getMessage(), error.getStatus());
        }
    }

}
