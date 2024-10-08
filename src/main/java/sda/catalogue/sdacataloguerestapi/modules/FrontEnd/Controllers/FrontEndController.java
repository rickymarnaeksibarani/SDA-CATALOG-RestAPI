package sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Controllers;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Dto.FrontEndDTO;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Dto.FrontEndRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Services.FrontEndService;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.WebAppRequestDto;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/front-end")
//@CrossOrigin(origins = "${spring.frontend}")
public class FrontEndController {
    @Autowired
    private FrontEndService frontEndService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchFrontEnd(FrontEndRequestDTO searchDTO) {

        try {
            PaginationUtil<FrontEndEntity, FrontEndEntity> result = frontEndService.getAllFrontendByPagination(searchDTO);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data front end!", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @GetMapping(value = "/{id_frontend}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFrontEndyById(
            @PathVariable("id_frontend") Long id_frontend
    ) {
        try {
            FrontEndEntity result = frontEndService.getFrontEndById(id_frontend);
            ApiResponse<FrontEndEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrieved data front end!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createFrontEnd(
            @Valid @RequestBody FrontEndDTO request
    ) {
        try {
            FrontEndEntity result = frontEndService.createFrontEnd(request);
            ApiResponse<FrontEndEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success create data front end!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PutMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateFrontEnd(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody FrontEndDTO request
    ) {
        try {
            FrontEndEntity result = frontEndService.updateFrontEnd(uuid, request);
            ApiResponse<FrontEndEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success update data front end!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }


    @DeleteMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteFrontEnd(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            frontEndService.deleteFrontEnd(uuid);
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK, "Success delete data front end!", "DELETED");
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}