package sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Controllers;

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
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Dto.TypeDatabaseDTO;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Dto.TypeDatabaseRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Entities.TypeDatabaseEntity;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Services.TypeDatabaseService;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.WebAppRequestDto;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/v1/type-database")
//@CrossOrigin(origins = "${spring.frontend}")
public class TypeDatabaseController {
    @Autowired
    private TypeDatabaseService typeDatabaseService;

    //Getting data PIC Developer with pagination
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchTypeDatabase(TypeDatabaseRequestDTO searchDTO) {

        try {
            PaginationUtil<TypeDatabaseEntity, TypeDatabaseEntity> result = typeDatabaseService.getAllTypeDatabaseByPagination(searchDTO);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data type database!", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTypeDatabaseByUuid(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            TypeDatabaseEntity result = typeDatabaseService.getTypeDatabaseByUuid(uuid);
            ApiResponse<TypeDatabaseEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrieved data type database!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTypeDatabase(
            @Valid @RequestBody TypeDatabaseDTO request
    ) {
        try {
            TypeDatabaseEntity result = typeDatabaseService.createTypeDatabase(request);
            ApiResponse<TypeDatabaseEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success create data type database!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PutMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTypeDatabase(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody TypeDatabaseDTO request
    ) {
        try {
            TypeDatabaseEntity result = typeDatabaseService.updateTypeDatabase(uuid, request);
            ApiResponse<TypeDatabaseEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success update data type database!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @DeleteMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteTypeDatabase(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            typeDatabaseService.deleteTypeDatabase(uuid);
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK, "Success delete data type database!", "DELETED");
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
