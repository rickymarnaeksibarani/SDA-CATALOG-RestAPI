package sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Controllers;

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
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Dto.BackEndDTO;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Services.PICDeveloperService;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.WebAppRequestDto;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/pic-developer")
//@CrossOrigin(origins = "${spring.frontend}")
public class PICDeveloperController {
    @Autowired
    private PICDeveloperService picDeveloperService;

    //Getting data PIC Developer with search and pagination
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchPIC(PICDeveloperRequestDTO searchDTO) {

        try {
            PaginationUtil<PICDeveloperEntity, PICDeveloperEntity> result = picDeveloperService.getAllPICDeveloperByPagination(searchDTO);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data pic developer!", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Getting data PIC Developer by UUID
    @GetMapping(value = "/{id_pic_developer}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPicDeveloperById(
            @PathVariable("id_pic_developer") Long id_pic_developer
    ) {
        try {
            PICDeveloperEntity result = picDeveloperService.getPICDeveloperByUUID(id_pic_developer);
            ApiResponse<PICDeveloperEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrieved data pic developer!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Creating data PIC Developer
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPICDeveloper(
           @RequestBody @Valid PICDeveloperDTO request
    ) {
        try {
            PICDeveloperEntity result = picDeveloperService.createPICDeveloper(request);
            ApiResponse<PICDeveloperEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success created data pic developer!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Updating data PIC Developer By UUID
    @PutMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePICDeveloper(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody PICDeveloperDTO request
    ) {
        try {
            PICDeveloperEntity result = picDeveloperService.updatePICDeveloper(uuid, request);
            ApiResponse<PICDeveloperEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success updated data pic developer!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Deleting data PIC Developer By UUID
    @DeleteMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePICDeveloperByUuid(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            picDeveloperService.deletePICDeveloper(uuid);
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK, "Success delete data back end!", "DELETED");
           return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
