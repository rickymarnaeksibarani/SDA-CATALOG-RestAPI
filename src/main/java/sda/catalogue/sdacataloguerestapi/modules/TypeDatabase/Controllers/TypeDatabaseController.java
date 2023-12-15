package sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.catalogue.sdacataloguerestapi.core.TangerangResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.TangerangResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.TangerangValidation.TangerangRequestException;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Entities.TypeDatabaseEntity;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Services.TypeDatabaseService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/type-database")
public class TypeDatabaseController {
    @Autowired
    private TypeDatabaseService typeDatabaseService;

    @GetMapping()
    public ResponseEntity<?> searchTypeDatabase(
            @RequestParam(name = "searchTerm", defaultValue = "") String searchTerm,
            @RequestParam(name = "page", defaultValue = "1") long page,
            @RequestParam(name = "size", defaultValue = "10") long size
    ) {
        try {
            PaginateResponse<List<TypeDatabaseEntity>> result = typeDatabaseService.searchAndPaginate(searchTerm, page, size);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Successfully retrieved data type database!", result), HttpStatus.OK);
        } catch (TangerangRequestException error) {
            return error.GlobalTangerangRequestException(error.getMessage(), error.getStatus());
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
        } catch (TangerangRequestException error) {
            return error.GlobalTangerangRequestException(error.getMessage(), error.getStatus());
        }
    }
}
