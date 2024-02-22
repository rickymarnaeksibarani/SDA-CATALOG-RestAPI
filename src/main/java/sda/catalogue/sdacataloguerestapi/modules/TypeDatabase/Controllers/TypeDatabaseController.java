package sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Controllers;

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
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Dto.TypeDatabaseDTO;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Entities.TypeDatabaseEntity;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Services.TypeDatabaseService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/type-database")
@CrossOrigin(origins = "${spring.frontend}")
public class TypeDatabaseController {
    @Autowired
    private TypeDatabaseService typeDatabaseService;

    //Getting data PIC Developer with pagination
    @GetMapping()
    public ResponseEntity<?> searchTypeDatabase(@ModelAttribute TypeDatabaseDTO searchDTO) {
        try {
            PaginationUtil<TypeDatabaseEntity, TypeDatabaseDTO> result = typeDatabaseService.getAllTypeDatabaseByPagination();
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data type database!", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @GetMapping("/{uuid}")
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

    @PostMapping()
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

    @PutMapping("/{uuid}")
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

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteTypeDatabase(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            TypeDatabaseEntity result = typeDatabaseService.deleteTypeDatabase(uuid);
            ApiResponse<TypeDatabaseEntity> response = new ApiResponse<>(HttpStatus.OK, "Success delete data type database!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
