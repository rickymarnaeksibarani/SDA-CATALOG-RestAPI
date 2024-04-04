package sda.catalogue.sdacataloguerestapi.modules.BackEnd.Controllers;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Dto.BackEndDTO;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Dto.BackEndRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Services.BackEndService;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.WebAppRequestDto;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.util.List;
import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("/api/v1/back-end")
//@CrossOrigin(origins = "${spring.frontend}")
public class BackEndController {
    @Autowired
    private BackEndService backEndService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchBackEnd(BackEndRequestDTO searchDTO) {

        try {
            PaginationUtil<BackEndEntity, BackEndEntity> result = backEndService.getAllBackendByPagination(searchDTO);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data back end!", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @GetMapping(value = "/{id_backend}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBackEndById(
            @PathVariable("id_backend") Long id_backend
    ) {
        try {
            BackEndEntity result = backEndService.getBackEndById(id_backend);
            ApiResponse<BackEndEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrieved data back end!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBackEnd(
            @Valid @RequestBody BackEndDTO request
    ) {
        try {
            BackEndEntity result = backEndService.createBackend(request);
            ApiResponse<BackEndEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success create data back end!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PutMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateBackEnd(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody BackEndDTO request
    ) {
        try {
            BackEndEntity result = backEndService.updateBackend(uuid, request);
            ApiResponse<BackEndEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success update data back end!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @DeleteMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteBackEnd(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            BackEndEntity result = backEndService.deleteBackend(uuid);
            ApiResponse<BackEndEntity> response = new ApiResponse<>(HttpStatus.OK, "Success delete data back end!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
