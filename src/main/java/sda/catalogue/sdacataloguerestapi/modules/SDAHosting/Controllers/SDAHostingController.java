package sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Controllers;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
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
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Dto.SDAHostingDTO;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Dto.SDAHostingRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Services.SDAHostingService;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.WebAppRequestDto;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/v1/sda-hosting")
//@CrossOrigin(origins = "${spring.frontend}")
public class SDAHostingController {
    @Autowired
    private SDAHostingService sdaHostingService;

    //Getting data SDA Hosting with search and pagination
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchSDAHosting(SDAHostingRequestDTO searchDTO) {

        try {
            PaginationUtil<SDAHostingEntity, SDAHostingEntity> result = sdaHostingService.getAllSDAHostingByPagination(searchDTO);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data sda hosting!", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @GetMapping(value = "/{id_sda_hosting}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSDAHostingById(
            @PathVariable("id_sda_hosting") Long id_sda_hosting
    ) {
        try {
            SDAHostingEntity result = sdaHostingService.getSDAHostingById(id_sda_hosting);
            ApiResponse<SDAHostingEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrieved data sda hosting!!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSDAHosting(
            @Valid @RequestBody SDAHostingDTO request
    ) {
        try {
            SDAHostingEntity result = sdaHostingService.createSDAHosting(request);
            ApiResponse<SDAHostingEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success create data sda hosting!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PutMapping(value = "/{uuid}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateSDAHosting(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody SDAHostingDTO request
    ) {
        try {
            SDAHostingEntity result = sdaHostingService.updateSDAHosting(uuid, request);
            ApiResponse<SDAHostingEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success update data sda hosting!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @DeleteMapping(value = "/{uuid}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteSDAHosting(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            sdaHostingService.deleteSDAHosting(uuid);
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK, "Success delete data", "DELETED");
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
